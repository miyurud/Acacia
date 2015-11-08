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
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.SequenceFileOutputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapred.lib.MultipleOutputs;
import org.apache.hadoop.mapred.lib.NullOutputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.Reducer.Context;

/**
 * 
 */

/**
 * @author miyuru
 *
 */
@SuppressWarnings({"unchecked"})
public class EdgelistPartitioner {
  static class SequenceFileMapper extends MapReduceBase implements Mapper<NullWritable, BytesWritable, Text, Text> {
    
    @SuppressWarnings("unused")
	private JobConf conf;
    
    @Override
    public void configure(JobConf conf) {
      this.conf = conf;
    }

    @Override
    public void map(NullWritable key, BytesWritable value,
        OutputCollector<Text, Text> output, Reporter reporter)
        throws IOException {
          	
    	String content = new String(value.getBytes(), "UTF8").trim();    	
    	StringTokenizer tok = new StringTokenizer(content, " ,\t\r\n");
    	int i = 1;
    	while(tok.hasMoreTokens()){
    		//String str = tok.nextToken();
    		//System.out.println(str + "====+++====>" + i);
    		output.collect(new Text(tok.nextToken()), new Text("" + i));
    		i++;
    	}
    } 
  }
  
  public static class MultipleOutputsInvertedReducer extends MapReduceBase implements org.apache.hadoop.mapred.Reducer<Text,Text,NullWritable,Text> {
		private MultipleOutputs multipleOutputs;
		
		@Override
		public void configure(JobConf conf){
			multipleOutputs = new MultipleOutputs(conf);
		}
			
		public void reduce(Text key, Iterator<Text> values, OutputCollector<NullWritable, Text> output, Reporter reporter) throws IOException {	
			@SuppressWarnings("rawtypes")
			OutputCollector collector = multipleOutputs.getCollector("partition", key.toString(), reporter);
			
			while(values.hasNext()){
				//Text ttx = values.next();
				//System.out.println("Writing --->" + ttx.toString());
				collector.collect(NullWritable.get(), values.next());
			}			
		}
		
		@Override
		public void close() throws IOException{
			multipleOutputs.close();
		}
		
		public void cleanup(@SuppressWarnings("rawtypes") Context context) throws IOException, InterruptedException{
			multipleOutputs.close();
		}
	}
  
  @SuppressWarnings("unused")
public static void main(String[] args) throws IOException {
	    JobConf conf = new JobConf(EdgelistPartitioner.class);
	    
	    if (conf == null) {
	      return;
	    }
		String dir1 = "/user/miyuru/merged";
		String dir2 = "/user/miyuru/merged-out";
		
		//We first delete the temporary directories if they exist on the HDFS
	    FileSystem fs1 = FileSystem.get(new JobConf());
	    //only delete dir2 because dir1 is uploaded externally.
		if(fs1.exists(new Path(dir2))){
			fs1.delete(new Path(dir2), true);
		}
		
	    conf.setInputFormat(WholeFileInputFormat.class);
	    conf.setOutputFormat(TextOutputFormat.class);
	    
	    WholeFileInputFormat.setInputPaths(conf, new Path(dir1));
	    SequenceFileOutputFormat.setOutputPath(conf, new Path(dir2));
	    
	    conf.setOutputKeyClass(Text.class);
	    conf.setOutputValueClass(Text.class);

	    conf.setMapperClass(SequenceFileMapper.class);
	    conf.setReducerClass(MultipleOutputsInvertedReducer.class);
	    conf.setOutputFormat(NullOutputFormat.class);
	    
	    conf.setJobName("EdgelistPartitioner");
	    
	    MultipleOutputs.addMultiNamedOutput(conf, "partition", TextOutputFormat.class, NullWritable.class, Text.class);
	    
	    JobClient.runJob(conf);
	  }
}
