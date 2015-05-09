package org.acacia.partitioner.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import java.util.StringTokenizer;

import org.acacia.csr.java.CSRConverter;
import org.acacia.csr.java.CSRConverter.InvertedMapper;
import org.acacia.csr.java.CSRConverter.InvertedReducer;
import org.acacia.csr.java.CSRConverter.NLinesInputFormat;
import org.acacia.csr.java.CSRConverter.ParagraphRecordReader;
import org.acacia.log.java.Logger_Java;
//import org.acacia.partitioner.hbase.java.HBaseInterface_Java;
import org.acacia.partitioner.index.PartitionIndexProtocol;
import org.acacia.server.AcaciaInstanceProtocol;
import org.acacia.util.java.Conts_Java;
import org.acacia.util.java.Utils_Java;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.LineRecordReader;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapred.lib.MultipleOutputs;
import org.apache.hadoop.mapred.lib.MultipleTextOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer.Context;

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

/**
 * This class is used to separate edges on an edge list to different partition files. The partition sequence (i.e., which vertex id belongs to which
 * partition) is calculated beforehand by other Acacia code.
 * @author miyuru
 *
 */

public class EdgeDistributor{
	public static class FileMapper extends MapReduceBase implements org.apache.hadoop.mapred.Mapper<LongWritable, Text, LongWritable, Text> {
		private String currentTable;
		private HBaseAdmin admin=null;
		private String zookeeperhost;
		private boolean zeroFlag = false;
		
		public void configure(JobConf job){
			currentTable = job.get("org.acacia.partitioner.hbase.table");
			zeroFlag = Boolean.parseBoolean(job.get("zero-flag"));
			zookeeperhost = getZookeeperLocation();
		}
				
		
		/**
		 * This method just pumps the edges from the edge list to the reducers. Also before emmitting KV paris, it resolves the corresponding vertex id from the
		 * HBase vertexmap table.
		 */
		
		public void map(LongWritable key, Text value, OutputCollector<LongWritable, Text> output, Reporter reporter) throws IOException {
			StringTokenizer itr = new StringTokenizer(value.toString(), " ,\t");
			long st = -1;
		    long ed = 0;
			long fromVertex = -1;
			long toVertex = -1;
 
			HashMap<Long, StringBuilder> vertsMap = new HashMap<Long, StringBuilder>();
			StringBuilder intermBuilder = null;
		    HashMap<Long,Long> uniqueVertStartHMap = new HashMap<Long,Long>();
		    HashMap<Long,Long> uniqueVertEndHMap = new HashMap<Long,Long>();

		    Long imS = null;
		    Long imE = null;
			
			while(itr.hasMoreTokens()) {
				String vvalue = itr.nextToken();
				
		    	//The following measure has been taken to void parsing the header of the EdgeList file
		    	try{
		    		if(zeroFlag){
		    			st = Long.parseLong(vvalue) + 1;
		    		}else{
		    			st = Long.parseLong(vvalue);
		    		}
		    	}
		    	catch(NumberFormatException exc){
		    		itr.nextToken();//Just ignore the next token and continue
		    		continue;
		    	}

				String vvalue2 = itr.nextToken();
				if(zeroFlag){
					ed = Long.parseLong(vvalue2) + 1;
				}else{
					ed = Long.parseLong(vvalue2);
				}


				intermBuilder = vertsMap.get(st);
				 
				 if(intermBuilder == null){
					 intermBuilder = new StringBuilder(); 
				 }
				 
				 intermBuilder.append(ed + " ");
				 vertsMap.put(st, intermBuilder);
			}
			
			for(Map.Entry<Long, StringBuilder> entry:vertsMap.entrySet() ){
				StringBuilder sb = entry.getValue();
				
				fromVertex = entry.getKey().longValue();
				
				output.collect(new LongWritable(fromVertex), new Text(sb.toString()));
			}
			
			reporter.progress();//Just to tell Hadoop that the map task is still in progress.
		}	
	}
	
	/**
	 * Given an original vertex from the edgelist file, this method converts the vertex to the mapped vertex in Acacia.
	 * @return
	 */
	
	private static String getZookeeperLocation() {
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
			System.out.println("AAAAAAAAAAAAAAAAAAA--->1");
			e.printStackTrace();
			System.out.println("AAAAAAAAAAAAAAAAAAA--->2");
		}
		
		result = prop.getProperty("org.acacia.partitioner.hbase.zookeeper.contacthost");
		
		return result;
	}
	
	public static class FileReducer  extends MapReduceBase implements org.apache.hadoop.mapred.Reducer<LongWritable,Text,LongWritable,Text> {
		
		private MultipleOutputs multipleOutputs;
		private String currentTable;
		private String zookeeperhost;
		private HBaseAdmin admin=null;
		private String contactHost;
		private short[] partitionIndex;
		private long totalVertexCount;
		private int initalPartitionID;
		private boolean zeroFlag = false;
		
		@Override
		public void configure(JobConf conf){
			multipleOutputs = new MultipleOutputs(conf);
			currentTable = conf.get("org.acacia.partitioner.hbase.table");
			zookeeperhost = getZookeeperLocation();
			contactHost = conf.get("org.acacia.partitioner.index.contacthost");
			totalVertexCount =  Long.parseLong(conf.get("vert-count"));
			initalPartitionID = Integer.parseInt(conf.get("initpartition-id"));
			zeroFlag = Boolean.parseBoolean(conf.get("zero-flag"));
			loadIndex();
		}
		
		private void loadIndex(){
			partitionIndex = new short[(int) totalVertexCount]; //There will be a big problem when we pass the margin of 4 billion vertices
						
			try{
			
			FileSystem fs1 = FileSystem.get(new JobConf());
			BufferedReader br = new BufferedReader(new InputStreamReader(fs1.open(new Path("/user/miyuru/merged"))));
			String line;
			line = br.readLine();
			int counter = 0;
			while(line != null){
				short val=Short.parseShort(line); // We assume that there are 65536 max number of partitions.
				partitionIndex[counter] = val;
				counter++;
				line=br.readLine();
			}
			br.close();
			}catch(IOException ec){
				ec.printStackTrace();
			}
		}
		
		public void reduce(LongWritable key, Iterator<Text> values,
				OutputCollector<LongWritable, Text> output, Reporter reporter)
				throws IOException {	
		
				//Here we first determine to which partition this key vertex belongs to. Next we check all the pairs of which the second vertex is also in
				//this partition. If so we store such pairs under this key's result file. If not we store that edge in the HBase table called EdgeMap.
			  
			   if (key != null){
					long kvval = getPartitionIDInMem((int)key.get());
					long kvval2 = -1;
					boolean resUpdate = false;
					
					String fileID = ""+(kvval + initalPartitionID);
				    OutputCollector<LongWritable, Text> collector = multipleOutputs.getCollector("partition", fileID, reporter);
					OutputCollector<LongWritable, Text> noPartCollector = multipleOutputs.getCollector("partition", "nopt", reporter);
						
					String res = null;
					
				    while (values.hasNext()){
				    	res = values.next().toString();
				    	StringTokenizer itr = new StringTokenizer(res);
				    	
				    	while(itr.hasMoreTokens()){
				    		long vvalue = Long.parseLong(itr.nextToken());
				    		kvval2 = getPartitionIDInMem((int)vvalue);
				    		
				    		if(kvval == kvval2){
				    			collector.collect(key, new Text("" + vvalue));
				    		}else{
					    		//In this case the edge is not in a particular file. Need to keep it in the edgemap.
					    						    			
				    			//noPartCollector.collect(key, new LongWritable(vvalue));
				    			//Aug 23 2014: Here we want to figureout the starting vertex partition id as well as ending vertex partition id
				    			//we need both those values embedded in the nopt file so that we can select the edges which start from a particular partition
				    			//or we can find the edges that end at a particular file.
				    			String startVertexPartitionID = ""+(getPartitionIDInMem((int)key.get()) + initalPartitionID);
				    			String endVertexPartitionID = ""+(getPartitionIDInMem((int)vvalue) + initalPartitionID);
				    			
				    			//Here to avoid making the key also as a Text data object we attach both the partition ids (for start and end vertices) in the value field.
				    			//The output format will be <startID><startPartitionID><endPartitionID><endID>
				    			//noPartCollector.collect(key, new Text(startVertexPartitionID + "\t" + endVertexPartitionID + "\t" + vvalue));
				    			
				    			//We do not need the key here so we modify the above statement as follows
				    			//noPartCollector.collect(null, new Text(startVertexPartitionID + "\t" + endVertexPartitionID + "\t" + vvalue));
				    			noPartCollector.collect(key, new Text(startVertexPartitionID + "\t" + endVertexPartitionID + "\t" + vvalue));
				    		}
				    		
				    	}
				    }
			   }else{
				   System.out.println("Got NULL for key.");
			   }
		}
					
		@Override
		public void close() throws IOException{
			multipleOutputs.close();
		}
		
		public void cleanup(Context context) throws IOException, InterruptedException{
			multipleOutputs.close();
		}

		/**
		 * This seems to be very naive implementation. May be we can find some better implementation.
		 * @param l
		 * @return
		 * @throws IOException 
		 */
		private Path selectPartitionFileofTheKeyVertex(long l) throws IOException {
			Path result = null;
			FileSystem fs1 = FileSystem.get(new JobConf());
			
			FileStatus[] files=fs1.listStatus(new Path("/user/miyuru/merged-out"));
			
			if(files != null){
				int len = files.length;
				boolean found = false;
				
				for(int i = 0; i < len; i++){
					String filePath = files[i].getPath().toString();
					if ((!(filePath.contains("_SUCCESS"))) && (!(filePath.contains("_logs")))){
						//Now read the file line aby line and check the existance of the vertex
						BufferedReader br = new BufferedReader(new InputStreamReader(fs1.open(files[i].getPath())));
						String line;
						line = br.readLine();
						while(line != null){
							long val=Long.parseLong(line);
							if(val == l){
								result = files[i].getPath();
								found = true;
								break;
							}
							line=br.readLine();
						}
						br.close();
					}
					
					if(found){
						break;
					}
				}
			}			
			
			return result;
		}
		
		
		
		/**
		 * This method returns true if the vertex is in the file.
		 * @param filePath
		 * @return
		 * @throws IOException 
		 */
		private boolean vertexIsInFile(long vertex, Path filePath) throws IOException{
			boolean result = false;
			FileSystem fs1 = FileSystem.get(new JobConf());
			BufferedReader br = new BufferedReader(new InputStreamReader(fs1.open(filePath)));
			String line;
			line = br.readLine();
			while(line != null){
				long val=Long.parseLong(line);
				if(val == vertex){
					result = true;
					break;
				}
				line=br.readLine();
			}
			
			br.close();
			
			return result;
		}
		
		/**
		 * This method returns true if the vertex is in the file.
		 * @param filePath
		 * @return
		 * @throws IOException 
		 */
		private int getPartitionID(long vertex) throws IOException{
			int result = -1;
			FileSystem fs1 = FileSystem.get(new JobConf());
			BufferedReader br = new BufferedReader(new InputStreamReader(fs1.open(new Path("/user/miyuru/merged"))));

			for(int i = 0; i < vertex-1; i++){
				br.readLine();
			}
			result = Integer.parseInt(br.readLine());
			br.close();
			
			return result;
		}
		
		private int getPartitionIDInMem(int vertex) throws IOException{
//			if(zeroFlag){
//				return partitionIndex[vertex];//If the indexing starts at zero, there is no need for suntracting one.
//			}else{
//				return partitionIndex[(vertex-1)];
//			}
			
			return partitionIndex[(vertex-1)];
		}
		
		private ConnectionObj connectToPartitionIndexService(){
			ConnectionObj result = new ConnectionObj();
			
			try{
				result.skt = new Socket(contactHost, Conts_Java.ACACIA_PARTITION_INDEX_PORT); //This goes to the local host
				result.out = new PrintWriter(result.skt.getOutputStream());
				result.reader = new BufferedReader(new InputStreamReader(result.skt.getInputStream()));
				String response = "";

				result.out.println(PartitionIndexProtocol.BATCH_PARTITION_ID_REQ); //Here we start the batch wise resolving session.
				result.out.flush();
			}catch(UnknownHostException e){
				Logger_Java.error(e.getMessage());
			}catch(IOException ec){
				Logger_Java.error(ec.getMessage());
			}
			
			return result;
		}
		
		private void disconnectFromPartitionIndexService(ConnectionObj obj){
			try{
				obj.out.println(PartitionIndexProtocol.BATCH_PARTITION_ID_REQ_DONE); //Here we start the batch wise resolving session.
				obj.out.flush();
				obj.skt.close();
			}catch(UnknownHostException e){
				Logger_Java.error(e.getMessage());
			}catch(IOException ec){
				Logger_Java.error(ec.getMessage());
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

	public static class ConnectionObj{
		public PrintWriter out;
		public BufferedReader reader;
		public Socket skt;
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

	            //System.out.println("ParagraphRecordReader::next() returns "+gotsomething+" after setting value to: ["+value.toString()+"]");
	            return gotsomething;
	        }

	        public long getPos() throws IOException {
	            return lineRecord.getPos();
	        }
	    }
	
	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
		String dir1 = "/user/miyuru/input";
		String dir2 = "/user/miyuru/edgedistributed-out";
		
//		//We first delete the temporary directories if they exist on the HDFS
	    FileSystem fs1 = FileSystem.get(new JobConf());
		if(fs1.exists(new Path(dir2))){
			fs1.delete(new Path(dir2), true);
		}
		
		//First job scans through the edge list and splits the edges in to separate files based on the partitioned vertex files.
		
		JobConf conf = new JobConf(EdgeDistributor.class);
	    conf.set("org.acacia.partitioner.hbase.zookeeper.quorum", args[0]);
	    conf.set("org.acacia.partitioner.hbase.table", args[1]);	 
	    conf.set("org.acacia.partitioner.index.contacthost", args[2]);
	    conf.set("vert-count", args[3]);
	    conf.set("initpartition-id", args[4]);
	    conf.set("zero-flag", args[5]);
	    conf.setOutputKeyClass(LongWritable.class);
	    conf.setOutputValueClass(Text.class);
	    conf.setMapperClass(FileMapper.class);
	    conf.setReducerClass(FileReducer.class);
	    //conf.setInputFormat(TextInputFormat.class);
	    conf.setInputFormat(NLinesInputFormat.class);
	    conf.setOutputFormat(TextOutputFormat.class);
	    conf.setNumReduceTasks(96); //Need to specify the number of reduce tasks explicitly. Otherwise it creates only one reduce task.
	
	    FileInputFormat.setInputPaths(conf, new Path(dir1));
	    FileOutputFormat.setOutputPath(conf, new Path(dir2));
	
	    MultipleOutputs.addMultiNamedOutput(conf, "partition", TextOutputFormat.class, NullWritable.class, Text.class);
	    
	    Job job = new Job(conf, "EdgeDistributor");
	    job.waitForCompletion(true); 
	    	
	    System.out.println("Done job EdgeDistribution");
	}
}

