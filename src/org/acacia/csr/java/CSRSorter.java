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

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapreduce.Mapper;
//import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

/**
 * This MapReduce class does the counting of number of vertices and edges
 * @author miyuru
 *
 */
public class CSRSorter {
	public static class CSRSortMapper  extends MapReduceBase implements org.apache.hadoop.mapred.Mapper<LongWritable, Text, LongWritable, LongWritable>{
		public void map(LongWritable arg0, Text value,
				OutputCollector<LongWritable, LongWritable> output, Reporter arg3)
				throws IOException {
			 String v = value.toString();
			 int edgeCount = v.trim().split(" |,").length;
			 
			 //This is corresponding to the edges
			 output.collect(new LongWritable(-1), new LongWritable(edgeCount));
			 //This is corresponding to the vertices. FOr each vertex we emit <-2,1> KV pair
			 output.collect(new LongWritable(-2), new LongWritable(1));	 
		}
	}

	public static class CSRSortReducer  extends MapReduceBase implements org.apache.hadoop.mapred.Reducer<LongWritable,LongWritable,LongWritable,LongWritable> {
		public void reduce(LongWritable key, Iterator<LongWritable> values,
				OutputCollector<LongWritable, LongWritable> output, Reporter arg3)
				throws IOException {	
			long res = 0l;
			    while (values.hasNext()){
			    	res += values.next().get();
			    }
			   
			    if(key.get() == -1){ //This need to be done only for edges
			    	res = res / 2; //This is because we count each edge twice
			    }
			    
			   output.collect(key, new LongWritable(res));	
		}
	}
}
