package org.acacia.server;

/**
 * Copyright 2015 Acacia Team

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.acacia.util.PlaceToNodeMapper;
import org.acacia.log.Logger;
import org.acacia.server.java.AcaciaInstanceFileTransferServiceSession;

public class AcaciaInstanceFileTransferService{
  private var srv:ServerSocket;
  private var runFlag:Boolean = true;
  private var port:Int = 0n;

  public static def main(args: Rail[String]) {
           
  }
  
  public def this(){
    //port = 7781;
    //port = Integer.parseInt(java.lang.System.getProperty("ACACIA_INSTANT_DATA_PORT"));
    port = PlaceToNodeMapper.getFileTransferServicePort(here.id);
    Console.OUT.println("AcaciaInstanceFileTransferService here.id:" + here.id + " and the port is : " + port);
  }

  public def start_running() : void {
    try{
      //			Logger_Java.info("Starting the file transfer service.");
      srv = new ServerSocket(port);
      //			Logger_Java.info("Done creating the file transfer service.");

      while(runFlag){
        var socket:Socket = srv.accept();
        Logger.info("Got a connection. Now serving...");
        var session:AcaciaInstanceFileTransferServiceSession = new AcaciaInstanceFileTransferServiceSession(socket);
        //session.addShutdownEventListener(new AcaciaShutdownEventListener(this));
        session.start();
        //This is something fancy we should do later in future.
        //session.addShutdownEventListener(new InstanceShutdownEventListener(this));
      }
    }catch(var e:BindException){
      Logger.error("AcaciaInstanceFileTransferService Error at " + here.id + " : " + e.getMessage());
    }catch(var e:IOException) {
      Logger.error("AcaciaInstanceFileTransferService Error at " + here.id + " " + e.getMessage());
    }

    Console.OUT.println("Exitting the AcaciaInstance server.");
  }

  public def shutdown() : void {
    Console.OUT.println("Acacia instance shuttingdown.");
  }
}