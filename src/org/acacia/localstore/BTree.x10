/**
 * Copyright 2015 Acacia Team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.acacia.localstore;

public class BTree implements Comparable(key, value){
	
	val sizeM:Int = 1000;    		// max children per B-tree node = M-1
	var root:Node;             		// root of the B-tree
	var height:Int;                 // height of the B-tree
	var keyValPairs:Int;            // number of key-value pairs
	
	//  node data type
	private class Node() {
		private nodeChildren:Int;       // number of children
		private a:Array[Int](1);		// the array of children
	
		private def this(k:Int ) {
			nodeChildren = k; 			// create a node with k children
		}											      
	}
	
	private class Entry {
		private key:Comparable;
		private value:Object ;
		private next:Node;     
		public Entry(key:Comparable, value:Object, next:Node ) {
			this.key   = key;
			this.value = value;
			this.next  = next;
		}
	}
	
	// constructor
	public def this () {
		root = new Node(0); 
	}
	
	// return number of key-value pairs in the B-tree
	public def getSize():Int {
		return keyValPairs;
	}
	
	// return height of B-tree
	public getHeight():Int { 
		return height;
	}
	
	// search for given key, return associated value; return null if no such key
	
	public def getValue(Key):Object {
		return search(root, key, HT); 
	}
	
	private searchValue(x:Node, key:Key,ht:Int):Object {

		var children:Array[Entry](1)= x.children;
		var value:Object;
		// external node
		if (ht == 0) {
			for (j:Int = 0; j < x.sizeM; j++) {
				if (key == children[j].key){
					
				}
			}
		}
		
		return null;
	}
	
}