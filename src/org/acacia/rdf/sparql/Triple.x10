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

public class Triple {
 	//class variables
 	private var subject:String;
 	private var predicate:String;
 	private var object:String;
 	private var pattern:Int;
 
  	//constructor
 	public def this(subject:String, predicate:String, object:String, pattern:Int){
 		this.subject = subject;
 		this.predicate = predicate;
 		this.object = object;
 		this.pattern = pattern;
 	}
  
 	public def getSubject():String{
 		return subject;
 	}
 
 	public def getPredicate():String{
 		return predicate;
 	}
 
 	public def getObject():String{
 		return object;
 	}
 
 	public def getPattern():Int{
 		return pattern;
 	}
}
