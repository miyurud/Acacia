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

package org.acacia.vertexcounter.java;

import java.util.ArrayList;

import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.IOException;

import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Conts_Java;

import java.net.Socket;
import java.net.ServerSocket;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;

import org.acacia.log.java.Logger_Java;

/**
 * Class VertexCounter keeps track of the unique vertex ids per graph. This is a
 * singleton interface. Therefore only one instance must be kept.
 */
public class VertexCounter {
    private boolean runFlag = true;
    private ServerSocket srv;
    private ArrayList sessions = new ArrayList();
    private VertexSingleton singletonObj = new VertexSingleton();

    /**
     * Default constructor
     */
    public VertexCounter() {

    }

    public void run() {
        try {
            // Logger_Java.info("Starting the VertexCounter service");
            srv = new ServerSocket(Conts_Java.ACACIA_VERTEXCOUNTER_PORT);
            // Logger_Java.info("Done creating VertexCounter service");
            int i = 0;
            while (runFlag) {
                // System.out.println("Waiting..." + i);
                Socket socket = srv.accept();
                // System.out.println("Got connection...");
                // System.out.println("Now serving...");

                VertexCounterServiceSession session = new VertexCounterServiceSession(
                        socket, singletonObj);
                // sessions.add(session);
                session.start();

                // System.out.println("Out from Async...");
                i++;
            }

            // while(runFlag){
            // var socket:Socket = srv.accept();
            // val skt = socket;
            // val session:AcaciaFrontEndServiceSession = new
            // AcaciaFrontEndServiceSession(skt);
            // sessions.add(session);
            // session.start();
            // }

        } catch (BindException e) {
            Logger_Java.error("Error : " + e.getMessage());
        } catch (IOException ec) {
            Logger_Java.error("Error : " + ec.getMessage());
        }
    }

    public static void main(String[] args) {
        VertexCounter obj = new VertexCounter();
        obj.run();

        while (true) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                // no worries
            }
        }
    }
}