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

package org.acacia.partitioner.java;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapreduce.Job;

/**
 * @author miyuru
 *
 *It seems the best way to spli up the lareg list of edges that accumulate into a single nopt file in HDFS is to
 *use a simple mapreduce job.
 */
public class NoptSplitter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(!validArgs(args)){
			printUsage();
			return;
		}
		//These are the temp paths that are created on HDFS
		String dir1 = "/user/miyuru/edgedistributed-out/nopt";
		String dir2 = "/user/miyuru/nopt-distributed";
		
		//We first delete the temporary directories if they exist on the HDFS
	    FileSystem fs1;
		try {
			fs1 = FileSystem.get(new JobConf());
	    
		    System.out.println("Deleting the dir : " + dir2);
		    
			if(fs1.exists(new Path(dir2))){
				fs1.delete(new Path(dir2), true);
			}
			
//			Path notinPath = new Path(dir2);
//			
//			if(!fs1.exists(notinPath)){
//				fs1.create(notinPath);
//			}
			
			JobConf conf = new JobConf(NoptSplitter.class);
//		    conf.setOutputKeyClass(Text.class);
//		    conf.setOutputValueClass(Text.class);
		    conf.setMapperClass(Map.class);
			conf.setCombinerClass(Reduce.class);
			conf.setReducerClass(Reduce.class);
		    
//			conf.setInputFormat(TextInputFormat.class);
//			conf.setOutputFormat(TextOutputFormat.class);

			FileInputFormat.setInputPaths(conf, new Path(dir1));
			FileOutputFormat.setOutputPath(conf, new Path(dir2));
			
			 Job job1 = new Job(conf, "nopt_splitter");
			 job1.setNumReduceTasks(Integer.parseInt(args[0])); //The most importnt point in this job
			 job1.waitForCompletion(true); 
			 
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, LongWritable, Text> {
        public void map(LongWritable key, Text value, OutputCollector<LongWritable, Text> output, Reporter reporter) throws IOException {
        			output.collect(key, value);
        }
}

    public static class Reduce extends MapReduceBase implements Reducer<LongWritable, Text, LongWritable, Text> {  	
		public void reduce(LongWritable arg0, Iterator<Text> arg1,
				OutputCollector<LongWritable, Text> arg2, Reporter arg3)
				throws IOException {
			
			while(arg1.hasNext()){
				arg2.collect(arg0, arg1.next());
			}
			
			arg3.progress();
		}
    }
	
	private static void printUsage() {
		System.out.println("-----NoptSplitter-----------");
		System.out.println("Usage : ");
		System.out.println(" jar bin/acacia.jar org.acacia.partitioner.java.NoptSplitter <number-of-splits>");
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
