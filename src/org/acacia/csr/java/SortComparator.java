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
 * Compares the composite key, {@link StockKey}. We sort by symbol ascendingly
 * and timestamp descendingly.
 * 
 * @author Jee Vang
 *
 */
public class SortComparator extends WritableComparator {

    public SortComparator() {
        super(LongWritable.class, true);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        LongWritable v1 = (LongWritable) a;
        LongWritable v2 = (LongWritable) b;

        if (v1.get() > v2.get()) {
            return 1;
        } else if (v1.get() < v2.get()) {
            return -1;
        } else {
            return 0;
        }
    }
}
