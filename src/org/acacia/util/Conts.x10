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

package org.acacia.util;

/**
 * Class Conts
 */
public class Conts {
	public static val BATCH_UPLOAD_FILE_LIST:String = "conf/batch-upload.txt";
	public static val ACACIA_SERVER_PROPS_FILE:String = "conf/acacia-server.properties";
    public static val ACACIA_SERVER_PUBLIC_HOSTS_FILE:String = "machines_public.txt";
    public static val ACACIA_SERVER_PRIVATE_HOSTS_FILE:String = "machines.txt";
    
    public static val BATCH_UPLOAD_CONFIG_FILE:String = "conf/batch-upload.txt";
    public static val ACACIA_SERVER_PROPS:String = "conf/acacia-server.properties";

    public static val ACACIA_PARTITION_INDEX_PORT:Int = 7776n;
    public static val ACACIA_FRONTEND_PORT:Int = 7777n;
    public static val ACACIA_BACKEND_PORT:Int = 7778n;
    public static val ACACIA_VERTEXCOUNTER_PORT:Int = 7779n;
    //The following ports will be calculated dynamically.
    public static val ACACIA_INSTANCE_PORT:Int = 7780n; //This is the starting point for instance port numbers
    //ACACIA_INSTANCE_DATA_PORT range will stat from the place where ACACIA_INSTANCE_PORT ports end.
    public static val ACACIA_INSTANCE_DATA_PORT:Int = 7781n;//This is the port through which the file transfer activities are conducted.
}