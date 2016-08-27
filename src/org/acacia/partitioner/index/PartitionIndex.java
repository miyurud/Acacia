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

package org.acacia.partitioner.index;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.acacia.log.java.Logger_Java;
import org.acacia.util.java.Conts_Java;
import org.acacia.vertexcounter.java.VertexCounter;
import org.acacia.vertexcounter.java.VertexCounterServiceSession;
import org.acacia.vertexcounter.java.VertexSingleton;

/**
 * There should be one partition index service per host. However, have to be
 * carefull because when the number of places increase the number of
 * simultaneous requests directed to this service increase considerably.
 * 
 * @author miyuru
 *
 */
public class PartitionIndex {
    private boolean runFlag = true;
    private ServerSocket srv;
    // private ArrayList sessions = new ArrayList();
    public PartitionIndexObject singletonObj = null;

    public PartitionIndex() {
        // Building the partition index object from the begining is not good
        // idea. Because we may run
        // Acacia without any intension of doing a graph uploading.
        // singletonObj = new PartitionIndexObject();
    }

    public void run() {
        try {
            srv = new ServerSocket(Conts_Java.ACACIA_PARTITION_INDEX_PORT);
            while (runFlag) {
                Socket socket = srv.accept();

                // At the first request we get to the partition index service we
                // construct the PartitionIndex object.
                if (singletonObj == null) {
                    singletonObj = new PartitionIndexObject();
                }

                PartitionIndexServiceSession session = new PartitionIndexServiceSession(
                        singletonObj, socket, this);
                // sessions.add(session);
                session.start();
            }
        } catch (BindException e) {
            Logger_Java.error("Error : " + e.getMessage());
        } catch (IOException ec) {
            Logger_Java.error("Error : " + ec.getMessage());
        }
    }

    public static void main(String[] args) {

    }
}