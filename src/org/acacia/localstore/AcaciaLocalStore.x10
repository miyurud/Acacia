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

package org.acacia.localstore;

import x10.util.HashMap;
import x10.util.HashSet;

public interface AcaciaLocalStore {

	public def loadGraph():Boolean;
	
	public def storeGraph():Boolean;
	
	public def getUnderlyingHashMap():HashMap[Long, HashSet[Long]];
	
	public def getOutDegreeDistributionHashMap():HashMap[Long, Long];
	
	public def initialize():void;
	
	public def addVertex(attributes:Rail[Any]):void;
	
	public def addEdge(startVid:Long, endVid:Long):void;
	
	public def getVertexCount():Long;
	
	public def getEdgeCount():Long;

}