package org.acacia.csr.java;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.acacia.csr.java.CSRConverter.InvertedMapper;
import org.acacia.csr.java.CSRConverter.InvertedReducer;
import org.acacia.csr.java.CSRConverter.ParagraphRecordReader;
import org.acacia.csr.java.WordCount.IntSumReducer;
import org.acacia.csr.java.WordCount.TokenizerMapper;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
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
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;

/**
 * This class checks for vertices that are not in a graph file..
 * @author miyuru
 *
 */
public class NotInFinder{
	  //public static class TokenizerMapper extends MapReduceBase implements Mapper<Object, Text, LongWritable, LongWritable>{
	public static class TokenizerMapper extends MapReduceBase implements org.apache.hadoop.mapred.Mapper<LongWritable, Text, LongWritable, LongWritable> {
   
   private final static LongWritable one = new LongWritable(1);
   private LongWritable word = new LongWritable();
     
   public void map(LongWritable key, Text value, OutputCollector<LongWritable, LongWritable> output, Reporter arg3) throws IOException {
	 //System.out.println("Got this : |" + value.toString() + "|");
	   
	   
     StringTokenizer itr = new StringTokenizer(value.toString(), " ,\t");
     long vv = 0;
     long prevval = -2;
     while (itr.hasMoreTokens()) {
    	 vv = Long.parseLong(itr.nextToken());
    	 
    	 if(prevval == -2){
    		 prevval = vv;
    		 continue;
    	 }else{
    		 prevval += 1;
    		 while(prevval < vv){
    			 word.set(prevval);
    			 output.collect(word, one);
    			 prevval++;
    		 }
    	 }
    	 
    	 prevval = vv;
    	 
       //word.set();
       //context.write(word, one);      
     }
   }
 }
 
 //public static class IntSumReducer extends Reducer<LongWritable,LongWritable,LongWritable,LongWritable> {
public static class IntSumReducer extends MapReduceBase implements org.apache.hadoop.mapred.Reducer<LongWritable,LongWritable,LongWritable,LongWritable> {
   private LongWritable result = new LongWritable();

   //public void reduce(LongWritable key, Iterable<LongWritable> values, 
  //                    Context context
  //                    ) throws IOException, InterruptedException {
  public void reduce(LongWritable key, Iterator<LongWritable> values, OutputCollector<LongWritable, LongWritable> output, Reporter reporter) throws IOException {
//     int sum = 0;
//     for (IntWritable val : values) {
//       sum += val.get();
//     }
//     result.set(sum);
     result.set(0);
     //context.write(key, result);
     output.collect(key, result);
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
	            String ln = null;
	            value.clear();
	            gotsomething = false;
	            
	            do {
	                retval = lineRecord.next(lineKey, lineValue);
	                if (retval) {
	                    if (lineValue.toString().length() > 0) {
	                    	ln = lineValue.toString();
	                    	lineValue.set(ln.split("	")[0]); //here we basically get the first element from a KV such as '4847570 -1'
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
 
 public static void main(String[] args) throws Exception {
	 String dir1 = "/user/miyuru/wcout"; 
	 String dir2 = "/user/miyuru/notinverts";
	//We first delete the temporary directories if they exist on the HDFS
    FileSystem fs1 = FileSystem.get(new JobConf());
    
	if(fs1.exists(new Path(dir2))){
		fs1.delete(new Path(dir2), true);
	}
	  
   JobConf conf = new JobConf();
   conf.setNumMapTasks(96);
   conf.setOutputKeyClass(LongWritable.class);
   conf.setOutputValueClass(LongWritable.class);
   conf.setMapperClass(TokenizerMapper.class);
   conf.setReducerClass(IntSumReducer.class);
   conf.setCombinerClass(IntSumReducer.class);
   conf.setInputFormat(NLinesInputFormat.class);
   conf.setOutputFormat(TextOutputFormat.class);
   FileInputFormat.setInputPaths(conf, new Path(dir1));
   FileOutputFormat.setOutputPath(conf, new Path(dir2));
   Job job = new Job(conf, "NotInFinder");
   job.setJarByClass(WordCount.class);
//   job.setMapperClass(TokenizerMapper.class);
//   job.setCombinerClass(IntSumReducer.class);
//   job.setReducerClass(IntSumReducer.class);
//   job.setOutputKeyClass(LongWritable.class);
//   job.setOutputValueClass(LongWritable.class);
   
   job.setSortComparatorClass(SortComparator.class);
   job.waitForCompletion(true); 
   
 }	
}