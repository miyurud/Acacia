/**
Copyright 2015 Acacia Team

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package org.acacia.csr.java;    

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.LineRecordReader;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reducer;
//import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
//import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapred.lib.TotalOrderPartitioner;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapred.lib.MultipleInputs;
import org.acacia.util.java.Utils_Java;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.acacia.csr.java.LineCount.Map;
import org.acacia.csr.java.LineCount.Reduce;
import org.acacia.csr.java.WordCount.IntSumReducer;
import org.acacia.csr.java.WordCount.TokenizerMapper;
import org.acacia.log.java.Logger_Java;
//import org.acacia.partitioner.hbase.java.Get;
//import org.acacia.partitioner.hbase.java.HBaseAdmin;
//import org.acacia.partitioner.hbase.java.HBaseInterface_Java;
//import org.acacia.partitioner.hbase.java.HColumnDescriptor;
//import org.acacia.partitioner.hbase.java.HTable;
//import org.acacia.partitioner.hbase.java.HTableDescriptor;
//import org.acacia.partitioner.hbase.java.Result;
import org.acacia.util.java.Conts_Java;
import org.acacia.vertexcounter.java.VertexCounterClient;
import org.acacia.vertexcounter.java.VertexCounterProtocol;

/**
 * Converts and edgelist to CSR format.
 * Note that in an edge list we know by default that all the vertices are connected with each other. Therefore no need of worrying whether to leave empty
 * lines for unconnected vertices. However, we can see that certain CSR files leave empty lines for their corresponding unconnected vertices.
 * @author miyuru
 *
 */
public class CSRConverter {
	public static class InvertedMapper extends MapReduceBase implements org.apache.hadoop.mapred.Mapper<LongWritable, Text, LongWritable, Text> {
		private Text word = new Text();
		private String zookeeperhost; 
		private String zk;
		private PrintWriter out=null;
		private BufferedReader reader=null;
		private boolean zeroFlag;
				
		@Override
		protected void finalize() {
			try{
				out.close();
				reader.close();
			}catch(UnknownHostException e){
				Logger_Java.error(e.getMessage());
			}catch(IOException ec){
				Logger_Java.error(ec.getMessage());
			}
		}
		
		public void configure(JobConf job){
			zeroFlag = Boolean.parseBoolean(job.get("zero-flag"));
			zookeeperhost = getZookeeperLocation();
		}
		
		/**
		 * Just keep on connected to the vertex counter service.
		 * @param contactHost
		 */
		private void connectToVertexCounterService(String contactHost) {
			try{
				Socket socket = new Socket(contactHost, Conts_Java.ACACIA_VERTEXCOUNTER_PORT);
				out = new PrintWriter(socket.getOutputStream());
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			}catch(UnknownHostException e){
				Logger_Java.error(e.getMessage());
				Logger_Java.error("Host is : " + contactHost);
			}catch(IOException ec){
				Logger_Java.error(ec.getMessage());
				Logger_Java.error("Host is : " + contactHost);
			}
		}

		private long getNextVertex(String graphID){
			long result = -1;

			try{
				String response = "";

				out.println(VertexCounterProtocol.TICKET);
				out.flush();
				
				response = reader.readLine();
				
				if((response != null)&&(response.equals(VertexCounterProtocol.SEND))){
					out.println(graphID);//Now we send the graph id
					out.flush();
				}
				
				response = reader.readLine();
				
				//This is the culprit that closs the socket
//				if(response != null){
//					out.close();
//				}
							
				if(!response.contains(VertexCounterProtocol.ERROR)){
					result = Long.parseLong(response);
				}
			}catch(UnknownHostException e){
				Logger_Java.error(e.getMessage());
			}catch(IOException ec){
				Logger_Java.error(ec.getMessage());
			}
			
			return result;
		}
						
		public void map(LongWritable key, Text value,OutputCollector<LongWritable, Text> output, Reporter reporter) throws IOException {
		    StringTokenizer itr = new StringTokenizer(value.toString(), " ,\t");	    	    
		    String stVert = null;
		    String edVert = null;
		    String r1 = null;
		    String r2 = null;
		    String cnt = null;
		    Text startVertex = null;
		    Text endVertex = null;
		    LongWritable lStartVertex = null;
		    LongWritable lEndVertex = null;
		    long st = 0;
		    long ed = 0;
		    Long imS = null;
		    Long imE = null;
		    boolean resUpdate = false;
		    
		    HashMap<Long,Long> uniqueVertStartHMap = new HashMap<Long,Long>();
		    HashMap<Long,Long> uniqueVertEndHMap = new HashMap<Long,Long>();
		    
		    while(itr.hasMoreTokens()) {
		    	stVert = itr.nextToken();
		    	edVert = itr.nextToken();
		    	
		    	if(Character.isDigit(stVert.charAt(0)) && Character.isDigit(edVert.charAt(0))){
		    	
		    		if(zeroFlag){
		    			startVertex = new Text("" + (Integer.parseInt(stVert.toString()) + 1));
		    			endVertex = new Text("" + (Integer.parseInt(edVert.toString()) + 1));
		    		}else{
		    			startVertex = new Text("" + (Integer.parseInt(stVert.toString())));
		    			endVertex = new Text("" + (Integer.parseInt(edVert.toString())));		    			
		    		}
		    		
		    		lStartVertex = new LongWritable();

		    		if(zeroFlag){
		    			lStartVertex.set(Integer.parseInt(stVert.toString()) + 1);
		    		}else{
		    			lStartVertex.set(Integer.parseInt(stVert.toString()));
		    		}
		    		lEndVertex = new LongWritable();
	
		    		if(zeroFlag){
		    			lEndVertex.set(Integer.parseInt(edVert.toString()) + 1);
		    		}else{
		    			lEndVertex.set(Integer.parseInt(edVert.toString()));
		    		}
		    		//CSR must output undirected graphs. Therefore we create two dirceted edges for each edge found.
		    		output.collect(lStartVertex, endVertex);
		    		output.collect(lEndVertex, startVertex);
		    	}
		    	
		    	if(Character.isDigit(stVert.charAt(0)) && (edVert.charAt(0) == '-')){
		    		endVertex = new Text(edVert);		    		
		    		lStartVertex = new LongWritable();
		    		
		    		if(zeroFlag){
		    			lStartVertex.set(Integer.parseInt(stVert.toString()) + 1);
		    		}else{
		    			lStartVertex.set(Integer.parseInt(stVert.toString()));
		    		}
		    		
		    		//CSR must output undirected graphs. Therefore we create two dirceted edges for each edge found.
		    		output.collect(lStartVertex, endVertex);
		    	}
		    }
		    
    		reporter.progress();//Just to tell Hadoop that the map task is still in progress.
		}

		private String getZookeeperLocation() {
			String result = null;
			
			Properties prop = new Properties();
			String confFileLoc = "/user/miyuru/acacia-server.properties";
			Path p = new Path(confFileLoc);
			
			try{
				FileSystem fs = FileSystem.get(new JobConf());	
				FSDataInputStream fdis = fs.open(p);
				prop.load(fdis);
				fdis.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			result = prop.getProperty("org.acacia.partitioner.hbase.zookeeper.contacthost");
			
			return result;
		}
	}
	
	public static class InvertedReducer extends MapReduceBase implements org.apache.hadoop.mapred.Reducer<LongWritable,Text,Text,Text> {
		public void reduce(LongWritable key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
		    //String line = "";
			StringBuilder lineBuilder = new StringBuilder();
			String tempStr = null;
		    boolean first = true;
		    
		    if(key.get() != -1){
			    while (values.hasNext()){
			    	if(first){			   
			    		tempStr = values.next().toString().trim();
			    		lineBuilder.append(tempStr);
			    		first = false;
			    		
			    		if(tempStr.equals("-1")){
			    			lineBuilder = new StringBuilder("");
			    		}
			    		
			    	}else{
			    		lineBuilder.append(" ");
			    		lineBuilder.append(values.next().toString());
			    	}
			    }
	
			    output.collect(null, new Text(lineBuilder.toString()));
		    }
		}
	}
	
	
	/**
	 * The following two classes are used to pump multiple lines of input text as one Key value pair to Hadoop map()
	 * @author miyuru
	 *
	 */
	public static class NLinesInputFormat extends TextInputFormat  
	{  
	   @Override
	   public RecordReader<LongWritable, Text> getRecordReader(InputSplit split, JobConf conf, Reporter reporter)throws IOException     {   
	        reporter.setStatus(split.toString());  
	        return new ParagraphRecordReader(conf, (FileSplit)split);
	    }
	}

	public static class ParagraphRecordReader implements RecordReader<LongWritable, Text> 
	{
			private int MAX_LINE_COUNT = 100; //This is the maximum number of lines in the input file that will be read at once.
	        private LineRecordReader lineRecord;
	        private LongWritable lineKey;
	        private Text lineValue;
	        
	        public ParagraphRecordReader(JobConf conf, FileSplit split) throws IOException {
	            lineRecord = new LineRecordReader(conf, split);
	            lineKey = lineRecord.createKey();
	            lineValue = lineRecord.createValue();
	        }

	        public void close() throws IOException {
	            lineRecord.close();
	        }

	        public LongWritable createKey() {
	            return new LongWritable();

	        }

	        public Text createValue() {
	            return new Text("");

	        }

	        public float getProgress() throws IOException {
	            return lineRecord.getPos();

	        }

	        public synchronized boolean next(LongWritable key, Text value) throws IOException {
	            boolean gotsomething;
	            boolean retval;
	            byte space[] = {' '};
	            int counter = 0;
	            value.clear();
	            gotsomething = false;
	            
	            do {
	                retval = lineRecord.next(lineKey, lineValue);
	                if (retval) {
	                    if (lineValue.toString().length() > 0) {
	                        byte[] rawline = lineValue.getBytes();
	                        int rawlinelen = lineValue.getLength();
	                        value.append(rawline, 0, rawlinelen);
	                        value.append(space, 0, 1);
	                        counter++;
	                    }
	                    gotsomething = true;
	                }else{
	                	break;
	                }
	            } while (counter < MAX_LINE_COUNT);

	            return gotsomething;
	        }

	        public long getPos() throws IOException {
	            return lineRecord.getPos();
	        }
	    }
	
	public static void main(String[] args) throws Exception {		
		if(!validArgs(args)){
			printUsage();
			return;
		}
		//These are the temp paths that are created on HDFS
		String dir1 = "/user/miyuru/csrconverter-output";
		String dir2 = "/user/miyuru/csrconverter-output-sorted";
		
		//We first delete the temporary directories if they exist on the HDFS
	    FileSystem fs1 = FileSystem.get(new JobConf());
	    
	    System.out.println("Deleting the dir : " + dir1);
	    
		if(fs1.exists(new Path(dir1))){
			fs1.delete(new Path(dir1), true);
		}
		
		System.out.println("Done deleting the dir : " + dir1);
		System.out.println("Deleting the dir : " + dir2);
		if(fs1.exists(new Path(dir2))){
			fs1.delete(new Path(dir2), true);
		}
		
		Path notinPath = new Path("/user/miyuru/notinverts/notinverts");
		
		if(!fs1.exists(notinPath)){
			fs1.create(notinPath);
		}
		
		System.out.println("Done deleting the dir : " + dir2);
		
		//Note on Aug 23 2014: Sometimes after this the mapReduce job hangs. need to see why.
		
		VertexCounterClient.setDefaultGraphID(args[3], args[2]);
		
		//First job creates the inverted index
		
		JobConf conf = new JobConf(CSRConverter.class);
	    conf.set("org.acacia.partitioner.hbase.zookeeper.quorum", args[1]);
	    conf.set("org.acacia.partitioner.hbase.table", args[2]);
	    conf.set("org.acacia.partitioner.hbase.contacthost", args[3]);
	    conf.setOutputKeyClass(LongWritable.class);
	    conf.setOutputValueClass(Text.class);
	    //conf.setMapperClass(InvertedMapper.class);
	    conf.setReducerClass(InvertedReducer.class);
	    //conf.setInputFormat(TextInputFormat.class);
	    conf.setInputFormat(NLinesInputFormat.class);
	    conf.setOutputFormat(TextOutputFormat.class);
	
	    //FileInputFormat.setInputPaths(conf, new Path(args[0]));
	    MultipleInputs.addInputPath(conf, new Path(args[0]), NLinesInputFormat.class, InvertedMapper.class);
	    MultipleInputs.addInputPath(conf, new Path("/user/miyuru/notinverts/notinverts"), TextInputFormat.class, InvertedMapper.class);
	    FileOutputFormat.setOutputPath(conf, new Path(dir1));
	    
	    //Also for the moment we turn-off the speculative execution
	    conf.setBoolean("mapred.map.tasks.speculative.execution", false);
	    conf.setBoolean("mapred.reduce.tasks.speculative.execution", false);
	    conf.setNumMapTasks(96);
	    conf.setNumReduceTasks(96);
	    conf.setPartitionerClass(VertexPartitioner.class);
	    conf.set("vertex-count", args[4]);
	    conf.set("zero-flag", args[5]);
	    Job job = new Job(conf, "csr_inverter");
	    job.setSortComparatorClass(SortComparator.class);
	    job.waitForCompletion(true); 
	}

	private static void printUsage() {
		System.out.println("-----CSRConverter-----------");		
		System.out.println("Usage : ");
		System.out.println(" hadoop jar acacia.jar org.acacia.csr.java.CSRConverter <inputfile-path-on-hdfs>");
		System.out.println("----------------------------");
	}

	/**
	 * This method validates whether the command line arguments are in correct format.
	 * @param args
	 * @return
	 */
	private static boolean validArgs(String[] args) {
		if(args.length < 1){
			return false;
		}
		return true;
	}
}