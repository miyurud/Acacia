package org.acacia.partitioner.java;

import java.io.File;
import java.io.IOException;

import org.acacia.csr.java.CSRConverter;
import org.acacia.csr.java.CSRConverter.InvertedMapper;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.fs.Path;

public class FileMerger{
	public static class FileMergerMapper  extends MapReduceBase implements org.apache.hadoop.mapred.Mapper<LongWritable, Text, LongWritable, LongWritable>{
		public void map(LongWritable arg0, Text value,
				OutputCollector<LongWritable, LongWritable> output, Reporter arg3)
				throws IOException {
			 String v = value.toString();
			 
			 Path pth = new Path("/user/miyuru/edgedistributed-out/" + v);
			 String newPath = "/user/miyuru/edgedistributed-out-filtered/" + v;
			 Path ppth = new Path(newPath);
			 FileSystem fs1 = FileSystem.get(new JobConf());
			 
//			 if(!fs1.exists(ppth)){
//				 fs1.mkdirs(ppth);
//			 }
			 
			 FileUtil.copyMerge(fs1, pth, fs1, ppth, false, new JobConf(), "");
		}
	}
	
	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(FileMerger.class);
		conf.setMapperClass(FileMergerMapper.class);
		String dir1 = "/user/miyuru/partlist";
		
	    FileSystem fs1 = FileSystem.get(new JobConf());
	    
		if(fs1.exists(new Path("/user/miyuru/filemerger-out"))){
			fs1.delete(new Path("/user/miyuru/filemerger-out"), true);
		}
		
		if(fs1.exists(new Path("/user/miyuru/edgedistributed-out-filtered"))){
			fs1.delete(new Path("/user/miyuru/edgedistributed-out-filtered"), true);
		}
		
		FileInputFormat.setInputPaths(conf, new Path(dir1));
		FileOutputFormat.setOutputPath(conf, new Path("/user/miyuru/filemerger-out"));
		
	    Job job = new Job(conf, "FileMerger");
	    job.waitForCompletion(true);		
	}
}