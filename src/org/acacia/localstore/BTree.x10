/**
 * Copyright 2015 Acacia Team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http: www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.acacia.localstore;

import x10.regionarray.*;
import x10.lang.*;
import x10.util.*;

  class BTree {
      private val sizeM:Int=1000n;    			//  max children per B-tree node = M-1
  	  private var root:Node;             		//  root of the B-tree
  	  private var height:Int;                   //	height of the B-tree
  	  private var keyValPairs:Int;             	// 	number of key-value pairs
  	
  	//node data type
  
  	  private static class Node {
  	  		var nodeChildren:Int;         		//number of children
  		  	var childrenArray:Rail[Entry];		 // the array of children
  		    	
  		  	public def this(k:Int) {
  				this.nodeChildren = k; 			 		// create a node with k children
  			}
  		  
  		  	public def getNodeChildrenArray():Rail[Entry]{
            	return childrenArray;
  			}
  	  }
  	
  	  private static class Entry {
  	  		private var key:Key;
  		  	private var value:Any;
  		  	private var next:Node;     
  
  	  		public def this(key:Key, value:Any, next:Node ) {
	  			this.key   = key;
	  			this.value = value;
	  			this.next  = next;
	  		}
  	  
  	  		def getKey():Key{
  	       		return key;
  	  		}
  		}
  	  
  	  class Key {  	       
  	  		private var key:Int; 
  	       
  	  		def Key(key:Int){
  	   			this.key = key;
  	  		}
  	  		def getKeyVal():Int{
  	  			return key;
  	  		}
  	  		def CompareTo(k:Int):Int{
		  	    var returnVal:Int = 0n;
		  	    if(k < key){ returnVal = -1n; }
		  	    if(k > key){returnVal = 1n;	}
		  	    
		  	    return returnVal;
	  	   	}
  	  }
  	  
  	  // constructor
  	  public def BTree() { 
  	  		root = new Node(0n); 
  	  }
  	  
	  // return number of key-value pairs in the B-tree
  	  public def getSize():Int {
	  		return keyValPairs;
	  }
  	
  	  // return height of B-tree
  	  public def getHeight():Int { 
  			return height;
  	  }
  	
  	  // search for given key, return associated value; return null if no such key
  	  public def get(key:Key):Any {
  	  		return search(root, key, height);
  	  }
    
  	  private def search(x:Node,key:Key,height:Int):Any {
  	  		var children:Rail[Entry] = x.getNodeChildrenArray();
  	  		// external node
  			if (height == 0n) {
    			for (j:Int = 0n; j < x.nodeChildren; j+1n) {
    				if(key.CompareTo(children(j).getKey().getKeyVal()) == 0n){
    					return children(j).value;
    				}
  	 			}
  			}
  
  			// internal node
  			else {
  				for (j:Int = 0n; j < x.nodeChildren; j+1n) {
  					if (j+1n == x.nodeChildren || key.CompareTo(children(j+1).getKey().getKeyVal()) < 0n){
  						return search(children(j).next, key, height-1n);
  					}
  				}
  			}
  			return null;
  		}
  
  		// insert key-value pair
  		// add code to check for duplicate keys
 	  	public def put(key:Key, value:Any):void {
  			var u:Node = insert(root, key, value, height); 
  			keyValPairs++;
  
  			if (u == null) {
  				return;
  			}
  	  	
		  	// need to split root
		  	splitRoot:Node = new Node(2n);
  			splitRoot.childrenArray(0n) = new Entry(root.childrenArray(0n).key, null, root);
  			splitRoot.childrenArray(1n) = new Entry(u.childrenArray(0n).key, null, u);
		  	root = splitRoot;
		  	height++;
  		}

   		private def insert(h:Node, key:Key, value:Any, height:Int):Node{
   			var j:Int;
   			var t:Entry = new Entry(key, value, null);

   			// external node
   			if (height == 0n) {
   				for (j = 0n; j < h.nodeChildren; j+1n) {
   					if (key.CompareTo(h.childrenArray(j).getKey().getKeyVal()) < 0n) 
   						break;
   				}
   			}

   			// internal node
   			else {
   			 	for (j = 0n; j < h.nodeChildren; j+1n) {
   			 		if (j+1n == h.nodeChildren || key.CompareTo(h.childrenArray(j+1).getKey().getKeyVal()) < 0n) {
   						var u:Node = insert(h.childrenArray(j+1n).next, key, value, height-1n);
   					 	if (u == null){
   					 		return null;
   					 	}
   					 	t.key = u.childrenArray(0n).key;
   					 	t.next = u;
   					 	break;
   					 }
   				}
  			}
   		
  			for (i:Int = h.nodeChildren; i > j; i-1n){
  				h.childrenArray(i) = h.childrenArray(i-1n);
  			}
  			h.childrenArray(j) = t;
  			h.nodeChildren++;
  
  			if (h.nodeChildren < sizeM) {
  				return null;
  			}else{         
  				return split(h);
  			}
   		}

  		// split node in half
   		private def split(h:Node):Node{
	  		t:Node = new Node(sizeM/2n);
	  		h.nodeChildren = sizeM/2n;
	  		for (j:Int = 0n; j < sizeM/2n; j+1n){
	  			t.childrenArray(j) = h.childrenArray(sizeM/2n+j); 
	  		}
	  		return t;    
  		}
   
   		public def delete(x:Node,key:Key, height:Int):void{
	   		var children:Rail[Entry] = x.getNodeChildrenArray();
	   		
	   		if (height == 0n) {
	   			for (j:Int = 0n; j < x.nodeChildren; j+1n) {
	   				if(key.CompareTo(children(j).getKey().getKeyVal()) == 0n){
	                	children(j) = null;
	   				}
	   			}
	   		} else {
	   			for (j:Int = 0n; j < x.nodeChildren; j+1n) {
	   				if (j+1n == x.nodeChildren){
	   					children(j) = null;
	   				}
	   
	   				if(key.CompareTo(x.childrenArray(j+1).getKey().getKeyVal()) < 0n){
	   					children(j) = children(j-1).next.childrenArray(size);
	   					children(j-1).next.childrenArray(size) = null;
	   				}
	   			}
	   		}   				  		
   		}
 }
  
 