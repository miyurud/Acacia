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

package org.acacia.partitioner.hadoop.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.acacia.util.java.Utils_Java;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;

/**
 * This is the Java class that represents a file on HDFS.
 * 
 * @author miyuru
 *
 */
public class HDFSFile_Java {

    static {
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
    }
    private static BufferedReader br;

    /**
     * This method opens a file on HDFS that can be read later.
     * 
     * @param path
     *            is the full path to the file that need to be opened.
     */
    public static void open(String path) {
        FileSystem fs;
        try {
            fs = FileSystem.get(new JobConf());
            // Master
            String masterNode = Utils_Java
                    .getAcaciaProperty("org.acacia.partitioner.hadoop.masters");
            br = new BufferedReader(new InputStreamReader(new URL("hdfs://"
                    + masterNode + ":9000" + path).openStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String readLine() {
        try {
            return br.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static void close() {
        try {
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}