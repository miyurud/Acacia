package org.acacia.csr.java;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Counters;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapred.Counters.Counter;
import org.apache.hadoop.mapreduce.Job;

/**
 * This is to get the number of edges
 * @author miyuru
 *
 */
public class LineCount {

    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, IntWritable, IntWritable> {
            private final static IntWritable one = new IntWritable(1);
            //private Text word = new Text("lns");
            private IntWritable iwr = new IntWritable(1);

            public void map(LongWritable key, Text value, OutputCollector<IntWritable, IntWritable> output, Reporter reporter) throws IOException {
            			output.collect(iwr, one);
            }
}

public static class Reduce extends MapReduceBase implements Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
 public void reduce(IntWritable key, Iterator<IntWritable> values, OutputCollector<IntWritable, IntWritable> output, Reporter reporter) throws IOException {
   int sum = 0;
   while (values.hasNext()) {
     sum += values.next().get();
   }
   output.collect(key, new IntWritable(sum));
 }
}

public static void main(String[] args) throws Exception {
	/*
	  String dir1 = "/user/miyuru/wcout";
	  String dir2 = "/user/miyuru/lcout";
		//We first delete the temporary directories if they exist on the HDFS
	    FileSystem fs1 = FileSystem.get(new JobConf());
	    
		if(fs1.exists(new Path(dir2))){
			fs1.delete(new Path(dir2), true);
		}
	
		 JobConf conf = new JobConf(LineCount.class);
		 conf.setJobName("LineCount");
		
		 conf.setOutputKeyClass(IntWritable.class);
		 conf.setOutputValueClass(IntWritable.class);
		
		 conf.setMapperClass(Map.class);
		 conf.setCombinerClass(Reduce.class);
		 conf.setReducerClass(Reduce.class);
		
		 conf.setInputFormat(TextInputFormat.class);
		 conf.setOutputFormat(TextOutputFormat.class);
		
		 FileInputFormat.setInputPaths(conf, new Path(dir1));
		 FileOutputFormat.setOutputPath(conf, new Path(dir2));
		
		 Job job = new Job(conf, "line count");
		 job.waitForCompletion(true); 
		 org.apache.hadoop.mapreduce.Counters cntr = job.getCounters();
		 System .out.println("Number of lines in the file" + cntr.findCounter("org.apache.hadoop.mapred.Task$Counter", "MAP_INPUT_RECORDS").getValue());
		 */
	
	long edgeCount = 0;
	  //String dir3 = "/user/miyuru/wcout";
	String dir4 = "/user/miyuru/lcout";
	String dir5 = "/user/miyuru/input";
	//We first delete the temporary directories if they exist on the HDFS
    FileSystem fs2 = FileSystem.get(new JobConf());
    
	if(fs2.exists(new Path(dir4))){
		fs2.delete(new Path(dir4), true);
	}
	
	 JobConf conf1 = new JobConf(LineCount.class);
	 conf1.setJobName("LineCount");

	 conf1.setOutputKeyClass(Text.class);
	 conf1.setOutputValueClass(IntWritable.class);

	 conf1.setMapperClass(Map.class);
	 conf1.setCombinerClass(Reduce.class);
	 conf1.setReducerClass(Reduce.class);

	 conf1.setInputFormat(TextInputFormat.class);
	 conf1.setOutputFormat(TextOutputFormat.class);

	 FileInputFormat.setInputPaths(conf1, new Path(dir5));
	 FileOutputFormat.setOutputPath(conf1, new Path(dir4));

	 Job job1 = new Job(conf1, "line count");
	 job1.setNumReduceTasks(0);
	 job1.waitForCompletion(true); 
	 org.apache.hadoop.mapreduce.Counters cntr = job1.getCounters();
	 edgeCount = cntr.findCounter("org.apache.hadoop.mapred.Task$Counter", "MAP_INPUT_RECORDS").getValue();
	 
	 File efile = new File("/tmp/efile");
	 
	 if(efile.exists()){
		efile.delete(); 
	 }
	 
	 PrintWriter writer = new PrintWriter("/tmp/efile", "UTF-8");
	 writer.println(edgeCount);
	 writer.flush();
	 writer.close();
	 
	 //edgeCount = edgeCount -1;//This is to remove the line number additionlly added to each edgelist file by HDFS. This is strange, but it happens.
	System.out.println("======>Edge count is : " + edgeCount);
	System.out.println("------Done Line Count---------------");
	}
}