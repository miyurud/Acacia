/**
 * Copyright 2012 Jee Vang 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0 
 *  
 *  Unless required by applicable law or agreed to in writing, software 
 *  distributed under the License is distributed on an "AS IS" BASIS, 
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *  See the License for the specific language governing permissions and 
 *  limitations under the License. 
 */
package org.acacia.csr.java;

import java.nio.ByteBuffer;

import javax.xml.soap.Text;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * Compares the composite key, {@link StockKey}.
 * We sort by symbol ascendingly and timestamp
 * descendingly.
 * @author Jee Vang
 *
 */
public class VertexComparator extends WritableComparator{

	public VertexComparator() {
		super(LongWritable.class);
		// TODO Auto-generated constructor stub
	}
	
//	public int compare(Text w1, Text w2) {
//		int k1 = Integer.parseInt(w1.toString());
//		int k2 = Integer.parseInt(w2.toString());
//		
//		if (k1 > k2){
//			return 1;
//		}else if (k1 == k2){
//			return 0;
//		}else{
//			return -1;
//		}
//	}
	
	@Override
	public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2){
		Long v1 = ByteBuffer.wrap(b1, s1, l1).getLong();
		Long v2 = ByteBuffer.wrap(b2, s2, l2).getLong();
		
		return v2.compareTo(v1) * (-1);
	}
}
