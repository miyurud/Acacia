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

package org.acacia.localstore.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.acacia.util.java.Utils_Java;

/**
 * Class AcaciaLocalStore
 */

public abstract class AcaciaLocalStore {
	
	public AcaciaLocalStore(int graphID, int partitionID){

	}
	
	/**
	 * This method creates a local Acacia data store.
	 */
    public abstract void initialize();
	
	public abstract void addVertex(Object[] attributes);
	
	public abstract void addEdge(Long startVid, Long endVid);
	
	public abstract long getVertexCount();

	public abstract long getEdgeCount();
}