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

package org.acacia.vertexcounter;

public class VertexCounterProtocol{
	public static String SEND = "send";		//Ask the client to send some data. This is used during a comminication session with the client.
	public static String EXIT = "exit";		//To exit from the query session
	public static String EXIT_ACK = "bye";
	public static String TICKET = "tckt";	//To request a ticket from the service. The ticket is basically a unique vertex id.
	public static String ERROR = "emsg";	//This is an error message sent from Acacia. Probably there is something wrong either in Acacia or in user's actions.
	public static String SAVE = "save";	//To persist the values recorded with the singleton on the MetaDB
	public static String DONE = "done";		//To indicate some operation (long running) is completed	
	public static String LOAD = "load";		//To make the vertexcountersession load the vertexcount information for each graph on Acacia
	public static String DEFAULT_GRAPH = "default-g"; //To set the dfault graph ID
}