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

import org.acacia.util.java.Utils_Java;

/**
 * This class is actually unnecessary because there is
 * org.acacia.partitioner.hadoop.HDFSInterface. However for the time being I
 * keep it here.
 * 
 * @author miyuru
 *
 */
public class HDFSInterface_Java {

    public static String[] listFiles(String pathOnHDFS) {
        String[] result = null;
        String hadoopHome = Utils_Java
                .getAcaciaProperty("org.acacia.partitioner.hadoop.home");

        return result;
    }
}