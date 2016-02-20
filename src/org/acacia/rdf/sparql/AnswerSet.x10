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

package org.acacia.rdf.sparql;

import x10.util.ArrayList;
import x10.util.HashSet;

public class AnswerSet {
 	private var answerSet:Rail[ArrayList[String]];
 	private var answerAdded:Rail[Boolean];
 	private var variableCount:Long;
 	private var isFirstTime:Boolean = true;
 	public static val Empty:String = "";
 	public def this(variableCount:Long){
 		this.variableCount	= variableCount;
 		answerAdded			= new Rail[Boolean](variableCount);
 		answerSet			= new Rail[ArrayList[String]](variableCount);
 		for(var k:Int=0n;k<variableCount;k++){
 			answerSet(k) = new ArrayList[String]();
 		}
 	}
 	public def mergeAnswer(answer:HashSet[String],i:Int){

 		val tempAnswerSet = new Rail[ArrayList[String]](variableCount);
 		for(var k:Int=0n;k<variableCount;k++){
 			tempAnswerSet(k) = new ArrayList[String]();
 		}
 		val length = answerSet(0n).size();
 		Console.OUT.println("Inside MergeAnswer Method");
 		if(!answerAdded(i)){
 			//cross two sets
 			if(isFirstTime){
 				isFirstTime = false;
 				Console.OUT.println("first time adding the results");
 				val itr = answer.iterator();
 				while(itr.hasNext()){
 					for(var k:Int=0n;k<variableCount;k++){
	 					if(k == i){
	 						tempAnswerSet(k).add(itr.next());
	 					}else{
	 						tempAnswerSet(k).add(Empty);
	 					}
 					}
 				}
 				Console.OUT.println("first time added the results");
 			}else{
	 			for(var j:Int=0n;j<length;j++){
	 				val itr = answer.iterator();
	 				while(itr.hasNext()){
	 					for(var k:Int=0n;k<variableCount;k++){
	 						if(k == i){
	 							tempAnswerSet(k).add(itr.next());
	 						}else{
	 							tempAnswerSet(k).add(answerSet(k).get(j));
	 						}
	 					}
	 				}
	 			}
 			}
 		}else{
 			//reduce the set
 			Console.OUT.println("reducing the results : "+i);
 			for(var j:Int=0n;j<length;j++){
 				if(answer.contains(answerSet(i).get(j))){
 					for(var k:Int=0n;k<variableCount;k++){
 						tempAnswerSet(k).add(answerSet(k).get(j));
 					}
 				}
 			}
 			Console.OUT.println("reduced the results");
 		}
 		answerAdded(i) = true;
 		answerSet = tempAnswerSet;
 	}
 
 	public def mergeAnswer(answer:HashSet[String],a:Int,b:Int){
 		val length = answerSet(0n).size();
 		val tempAnswerSet = new Rail[ArrayList[String]](variableCount);
 		for(var k:Int=0n;k<variableCount;k++){
 			tempAnswerSet(k) = new ArrayList[String]();
 		}
 		if(answerAdded(a) && answerAdded(b)){
 			//check both and reduce
 			for(var j:Int=0n;j<length;j++){
 				//Console.OUT.println(answerSet(a).get(j)+","+answerSet(b).get(j));
 				if(answer.contains(answerSet(a).get(j)+","+answerSet(b).get(j))){
 					for(var k:Int=0n;k<variableCount;k++){
 						tempAnswerSet(k).add(answerSet(k).get(j));
 					}
 				}
 			}
 		}else if(answerAdded(a)){
 			//check only a
 			val itr = answer.iterator();
 			while(itr.hasNext()){
 				val value = itr.next().split(",");
 				if(answerSet(a).contains(value(0))){
 					var index:Long = answerSet(a).indexOf(0,value(0));
 					while(index!=-1){
 						for(var k:Int=0n;k<variableCount;k++){
 						if(k == b){
 							tempAnswerSet(k).add(value(1));
 						}else{
 							tempAnswerSet(k).add(answerSet(k).get(index));
 						}
 						}
 						index = answerSet(a).indexOf(index+1,value(0));
 					}
 				}
 			}
 			answerAdded(b) = true;
 		}else if(answerAdded(b)){
 			//check only b
 			val itr = answer.iterator();
 			while(itr.hasNext()){
 				val value = itr.next().split(",");
 				if(answerSet(b).contains(value(1))){
 					var index:Long = answerSet(b).indexOf(0,value(1));
 					while(index!=-1){
 						for(var k:Int=0n;k<variableCount;k++){
 							if(k == a){
 								tempAnswerSet(k).add(value(0));
 							}else{
 								tempAnswerSet(k).add(answerSet(k).get(index));
 							}
 						}
 						index = answerSet(b).indexOf(index+1,value(1));
 					}
 				}
 			}
 			answerAdded(a) = true;
 		}else if(isFirstTime){
   			isFirstTime = false;
 			val itr = answer.iterator();
 			while(itr.hasNext()){
 				val value = itr.next().split(",");
 				for(var k:Int=0n;k<variableCount;k++){
 					if(k == a){
 						tempAnswerSet(k).add(value(0));
 					}else if(k == b){
 						tempAnswerSet(k).add(value(1));
 					}else{
 						tempAnswerSet(k).add(Empty);
 					}
 				}
 			}
 			answerAdded(a) = true;
 			answerAdded(b) = true;
 		}else{
 			//cross the pair
 			for(var j:Int=0n;j<length;j++){
 				val itr = answer.iterator();
 				while(itr.hasNext()){
 					val value = itr.next().split(",");
 					for(var k:Int=0n;k<variableCount;k++){
 						if(k == a){
 							tempAnswerSet(k).add(value(0));
 						}else if(k == b){
 							tempAnswerSet(k).add(value(1));
 						}else{
 							tempAnswerSet(k).add(answerSet(k).get(j));
 						}
 					}
 				}
 			}
 			answerAdded(a) = true;
 			answerAdded(b) = true;
 		}
 		answerSet = tempAnswerSet;
 	}
 
 	public def getAnswerSet():Rail[ArrayList[String]]{
 		return answerSet;
 	}
}