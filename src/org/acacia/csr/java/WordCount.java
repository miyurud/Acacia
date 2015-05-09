package org.acacia.csr.java;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.GenericOptionsParser;

import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;

/*
 * This job is used to count the number of unique vertices in the graph.
 */

public class WordCount {

  public static class TokenizerMapper 
       extends Mapper<Object, Text, LongWritable, LongWritable>{
    
    private final static LongWritable one = new LongWritable(1);
    private LongWritable word = new LongWritable();
      
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString(), " ,\t\r\n");
      
      while (itr.hasMoreTokens()) {
        //word.set(Long.parseLong(itr.nextToken()) + 1);
    	  word.set(Long.parseLong(itr.nextToken()));
        context.write(word, one);      
      }
    }
  }
  
  public static class IntSumReducer 
       extends Reducer<LongWritable,LongWritable,LongWritable,LongWritable> {
    private LongWritable result = new LongWritable();

    public void reduce(LongWritable key, Iterable<LongWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
//      int sum = 0;
//      for (IntWritable val : values) {
//        sum += val.get();
//      }
//      result.set(sum);
      result.set(-1);
      context.write(key, result);
    }
  }

  public static void main(String[] args) throws Exception {
	  /*
	  String dir1 = "/user/miyuru/wcout";
		//We first delete the temporary directories if they exist on the HDFS
	    FileSystem fs1 = FileSystem.get(new JobConf());
	    
		if(fs1.exists(new Path(dir1))){
			fs1.delete(new Path(dir1), true);
		}
	  
    JobConf conf = new JobConf();
    conf.setNumMapTasks(96);
    Job job = new Job(conf, "word count");
    job.setJarByClass(WordCount.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(LongWritable.class);
    job.setOutputValueClass(LongWritable.class);
    
    job.setSortComparatorClass(SortComparator.class);
    FileInputFormat.addInputPath(job, new Path("/user/miyuru/input"));
    FileOutputFormat.setOutputPath(job, new Path(dir1));
    job.waitForCompletion(true); 
    */
	  
		String dir3 = "/user/miyuru/wcout";
		String dir5 = "/user/miyuru/input";
		//We first delete the temporary directories if they exist on the HDFS
		FileSystem fs3 = FileSystem.get(new JobConf());
		
		if(fs3.exists(new Path(dir3))){
			fs3.delete(new Path(dir3), true);
		}
		  
	    JobConf conf3 = new JobConf();
	    conf3.setNumMapTasks(96);
	    FileInputFormat.addInputPath(conf3, new Path(dir5));
	    FileOutputFormat.setOutputPath(conf3, new Path(dir3));
	    Job job3 = new Job(conf3, "word count");
	    job3.setJarByClass(WordCount.class);
	    job3.setMapperClass(TokenizerMapper.class);
	    job3.setCombinerClass(IntSumReducer.class);
	    job3.setReducerClass(IntSumReducer.class);
	    job3.setOutputKeyClass(LongWritable.class);
	    job3.setOutputValueClass(LongWritable.class);
	    
	    job3.setSortComparatorClass(SortComparator.class);

	    job3.waitForCompletion(true); 
	    
	    
		 PrintWriter writer;
		try {
			writer = new PrintWriter("/tmp/wfile", "UTF-8");
			writer.println("");
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println("------Done Word Count---------------");  
	  
	  
  }
}