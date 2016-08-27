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

package org.acacia.util.java;

public class Conts_Java {
    public static final String BATCH_UPLOAD_CONFIG_FILE = "conf/batch-upload.txt";
    public static final String ACACIA_SERVER_PROPS = "conf/acacia-server.properties";

    public static final int ACACIA_PARTITION_INDEX_PORT = 7776;
    public static final int ACACIA_FRONTEND_PORT = 7777;
    public static final int ACACIA_BACKEND_PORT = 7778;
    public static final int ACACIA_VERTEXCOUNTER_PORT = 7779;
    // The following ports will be calculated dynamically.
    public static final int ACACIA_INSTANCE_PORT = 7780; // This is the starting
                                                         // point for
                                                         // instance
                                                         // port numbers
    // ACACIA_INSTANCE_DATA_PORT range will stat from the place where
    // ACACIA_INSTANCE_PORT ports end.
    public static final int ACACIA_INSTANCE_DATA_PORT = 7781;// This is the port
                                                             // through which
                                                             // the file
                                                             // transfer
                                                             // activities
                                                             // are
                                                             // conducted.
}