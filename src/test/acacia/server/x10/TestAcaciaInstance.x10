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

package test.acacia.server.x10;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.acacia.server.AcaciaInstance;
import org.acacia.server.AcaciaInstanceFileTransferService;

import org.acacia.log.java.Logger_Java;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import org.acacia.server.AcaciaInstance;

public class TestAcaciaInstance{
	public static def main(val args:Rail[String]):Int {
		if(java.lang.System.getProperty("logFileName") == null){
			java.lang.System.setProperty("logFileName",java.lang.System.getenv("HOSTNAME"));
		}
		
		//We need to check whether the user has specified the instance port number and data port number.
		if(java.lang.System.getProperty("ACACIA_INSTANCE_PORT") == null){
			Logger_Java.info("Could not find the port number to run Acacia Instance. Now exitting ");
			return 0n;
		}
		
		if(java.lang.System.getProperty("ACACIA_INSTANCE_DATA_PORT") == null){
			Logger_Java.info("Could not find the port number for Acacia Instance's data transfer service. Now exitting ");
			return 0n;
		}
		
		// Logger_Java.info("---AAAA---");
		
// 		Thread t1 = new Thread(){
// 			public void run(){
// 				AcaciaInstance server = new AcaciaInstance();
// 				try {
// 					server.start_running();
// 				} catch (UnknownHostException e) {
// 					e.printStackTrace();
// 				}
// 				
// //				while(true){
// //					try {
// //						Thread.currentThread().sleep(1000);
// //					} catch (InterruptedException e) {
// //						e.printStackTrace();
// //					}//Sleep this many milliseconds
// //				}
// 			}
// 		};
    // finish{
        async{
        				val server:AcaciaInstance = new AcaciaInstance();
        				try {
        					server.start_running();
        				} catch (val e:UnknownHostException) {
        					e.printStackTrace();
        				}
        				
        //				while(true){
        //					try {
        //						Thread.currentThread().sleep(1000);
        //					} catch (InterruptedException e) {
        //						e.printStackTrace();
        //					}//Sleep this many milliseconds
        //				}
        }


		// Logger_Java.info("---BBBB---");
		
// 		Thread t2 = new Thread(){
// 			public void run(){
// 				AcaciaInstanceFileTransferService service = new AcaciaInstanceFileTransferService();
// 				try {
// 					service.start_running();
// 				} catch (UnknownHostException e) {
// 					e.printStackTrace();
// 				}
// 				
// //				while(true){
// //					try {
// //						Thread.currentThread().sleep(1000);
// //					} catch (InterruptedException e) {
// //						e.printStackTrace();
// //					}//Sleep this many milliseconds
// //				}
// 			}
// 		};

        async{
        				val service:AcaciaInstanceFileTransferService = new AcaciaInstanceFileTransferService();
        				try {
        					service.start_running();
        				} catch (val e:UnknownHostException) {
        					e.printStackTrace();
        				}
        				
        //				while(true){
        //					try {
        //						Thread.currentThread().sleep(1000);
        //					} catch (InterruptedException e) {
        //						e.printStackTrace();
        //					}//Sleep this many milliseconds
        //				}
        }

		// Logger_Java.info("---CCCC---");
		// t1.start();
		// Logger_Java.info("---DDDDD---");
		try{
			Logger_Java.info("Started AcaciaInstance service at " + InetAddress.getLocalHost().getHostName());
		} catch (val e:UnknownHostException) {
			e.printStackTrace();
		}
		// Logger_Java.info("---FFFFF---");
		// t2.start();
		// Logger_Java.info("---EEEEE---");
		// while(true){
		// 	try {
		// 		Thread.currentThread().sleep(1000);
		// 	} catch (val e:InterruptedException) {
		// 		e.printStackTrace();
		// 	}//Sleep this many milliseconds
		// }
	//}
     
     return 0n;
	}
}