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

import org.acacia.localstore.java.AcaciaHashMapLocalStore;
import org.acacia.localstore.java.AcaciaLocalStore;

public class TestAcaciaLocalStore{
	public static void main(String[] args){
//		AcaciaLocalStore localStore = new AcaciaLocalStore();
//		localStore.run();
		
		AcaciaHashMapLocalStore localStore = new AcaciaHashMapLocalStore(1,480);
		localStore.loadGraph();
		//localStore.addEdge(100l, 101l);
		//localStore.storeGraph();
		System.out.println(localStore.toString());
	}
}