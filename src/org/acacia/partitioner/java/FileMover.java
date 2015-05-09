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

public class FileMover{
	public static class FileMoverMapper  extends MapReduceBase implements org.apache.hadoop.mapred.Mapper<LongWritable, Text, LongWritable, LongWritable>{
		public void map(LongWritable arg0, Text value,
				OutputCollector<LongWritable, LongWritable> output, Reporter arg3)
				throws IOException {
			 String v = value.toString();
			 String v2 = null;
			 String v3 = null;
			 String[] tmp = v.split("partition_");
			 tmp = tmp[1].split("-");
			 v3 = tmp[0];
			 tmp = v.split("/");
			 v2 = tmp[tmp.length - 1];
			 
			 Path pth = new Path("/user/miyuru/edgedistributed-out/" + v3);
			 String newPath = "/user/miyuru/edgedistributed-out/" + v3 + "/" + v2;
			 FileSystem fs1 = FileSystem.get(new JobConf());
			 if(!fs1.exists(pth)){
				 fs1.mkdirs(pth);
			 }
			 
			 System.out.println("AAAAAAAAAAAAAAAAA-->"+newPath);
			 
			 //FileUtil.replaceFile(new File(v), new File(newPath));
			 FileUtil.copy(fs1, new Path(v), fs1, new Path(newPath), false, new JobConf());
		}
	}
	
	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(FileMover.class);
		conf.setMapperClass(FileMoverMapper.class);
		String dir1 = "/user/miyuru/partfilelist";
		
	    FileSystem fs1 = FileSystem.get(new JobConf());
	    
		if(fs1.exists(new Path("/user/miyuru/filemover-out"))){
			fs1.delete(new Path("/user/miyuru/filemover-out"), true);
		}
		
		FileInputFormat.setInputPaths(conf, new Path(dir1));
		FileOutputFormat.setOutputPath(conf, new Path("/user/miyuru/filemover-out"));
		
	    Job job = new Job(conf, "FileMover");
	    job.waitForCompletion(true);		
	}
}