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

package org.acacia.backend;

public class AcaciaBackEndProtocol {
    //This protocol will too have a handshaking session. This is to enable the server track with whom it communicated.
    public static HANDSHAKE:String = "hske";		//To notify the server the host name of this worker
    public static HANDSHAKE_OK:String = "hske-ok";		//Response to say it is ready for handshaking.
    public static OK:String = "ok";		//To check if the status is ok
    public static RUOK:String = "ruok";		//To check if the status is ok
	public static IMOK:String = "imok";		//Response to check if the status is ok
	public static EXIT:String = "exit";		//To exit from the query session
	public static EXIT_ACK:String = "bye";
    public static SEND:String = "send";		//Ask the client to send some data. This is used during a comminication session with the client.
    public static OUT_DEGREE_DISTRIBUTION_FOR_PARTITION:String = "podg";
    public static IN_DEGREE_DISTRIBUTION_FOR_PARTITION:String = "pidg"; //This commad gets the in degree distribution from the external world to this partition
    public static WORLD_ONLY_AUTHFLOW_FOR_PARTITION:String = "woaf";
    public static WORLD_TO_LOCAL_AUTHFLOW_FOR_PARTITION:String = "wlaf";
    public static LOCAL_TO_WORLD_AUTHFLOW_FOR_PARTITION:String = "lwaf";
    public static WORLD_TO_LOCAL_FLOW_FROMIDS:String = "wlfi"; //This command gets all the vertices connected with the external graph and their corresponding fromIDs
    public static INTERSECTING_TRIANGLE_COUNT:String = "trii"; //This is the command to count the number of traingles available on the global graph.
    public static DONE:String = "done";
    public static PARTITIONS_ON_HOST:String = "pohs"; //The command to get the list of partitions on particular host
}