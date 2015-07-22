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

package test.acacia.localstore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import org.acacia.localstore.java.AcaciaHashMapLocalStore;
import org.acacia.localstore.java.AcaciaLocalStore;
import org.acacia.query.algorithms.triangles.Triangles;
import org.acacia.util.java.Utils_Java;

import com.google.common.base.Splitter;

public class TestAcaciaLocalStore{
	public static void main(String[] args){
//		AcaciaLocalStore localStore = new AcaciaLocalStore();
//		localStore.run();
		
//		AcaciaHashMapLocalStore localStore = new AcaciaHashMapLocalStore(276,934);
//		localStore.loadGraph();
//		
//		String result = Triangles.run(localStore, "276", "934", null);
		
		//AcaciaHashMapLocalStore localStore = loadDataSet("/home/miyurud/Acacia/graphs/Roadnet-PA/roadNet-PA.txt");
		///home/miyurud/Acacia/debug/trian/unified
		AcaciaHashMapLocalStore localStore = loadDataSet("/home/miyurud/software/x10dt/workspace/Acacia/unified.txt");
		System.out.println("vertex count : "+localStore.getVertexCount());
		System.out.println("edge count : "+localStore.getEdgeCount());
		
		String result = Triangles.run(localStore, null);
		
		//localStore.addEdge(100l, 101l);
		//localStore.storeGraph();
		System.out.println(result);
	}
	
	private static AcaciaHashMapLocalStore loadDataSet(String filePath){
		BufferedReader br;
		boolean zeroVertFlag = false;
		Integer firstVertex = -1;
		Integer secondVertex = -1;
		AcaciaHashMapLocalStore localStore = new AcaciaHashMapLocalStore("/home/miyurud/Acacia/temp/test/");
		
		try{
		    br = new BufferedReader(new FileReader(filePath), 10 * 1024 * 1024);
		    String line = br.readLine();
			Splitter splitter = null;
			
			if(line != null){
				if(line.contains(" ")){
					splitter = Splitter.on(' ');
				}else if(line.contains("\t")){
					splitter = Splitter.on('\t');
				}else if(line.contains(",")){
					splitter = Splitter.on(',');
				}else{
					System.out.println("Error : Could not find the required splitter character ...");
				}
			}
			
			//Here first we need to scan through the file one round and see whether the file contains a zero vertex.
			//If it contains a zero vertex we have to treat it separately. So that will be done in the second round.
			
			while (line != null) {
		    	Iterator<String> dataStrIterator = splitter.split(line).iterator();
		    	firstVertex = Integer.parseInt(dataStrIterator.next());
		    	secondVertex = Integer.parseInt(dataStrIterator.next());
		    	
		    	if((firstVertex == 0)||(secondVertex == 0)){
		    		zeroVertFlag = true;
		    		//Once we found a zero vertex, we should break
		    		break;
		    	}
		    	
				line = br.readLine();
		    	while((line != null)&&(line.trim().length() == 0)){
		    		line = br.readLine();
		    	}
			}
			
			br.close();
			
			//Next, we start loading the graph
			
			br = new BufferedReader(new FileReader(filePath), 10 * 1024 * 1024);
			line = br.readLine();
			
		    while (line != null) {
		    	Iterator<String> dataStrIterator = splitter.split(line).iterator();		
		    	firstVertex = Integer.parseInt(dataStrIterator.next());
		    	secondVertex = Integer.parseInt(dataStrIterator.next());
		    	
		    	if(firstVertex == secondVertex){
					line = br.readLine();
			    	while((line != null)&&(line.trim().length() == 0)){
			    		line = br.readLine();
			    	}
			    	continue;
		    	}
		    	
		    	if(zeroVertFlag){
			    	firstVertex = firstVertex + 1;
			    	secondVertex = secondVertex + 1;
				}
		    	
		    	localStore.addEdge(new Long(firstVertex.intValue()), new Long(secondVertex.intValue()));
		    			    	
				line = br.readLine();
		    	while((line != null)&&(line.trim().length() == 0)){
		    		line = br.readLine();
		    	}
		    }
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return localStore;
	}
}