/**
 * Class HashMapSerializer
 */

package org.acacia.localstore;

import x10.util.HashMap;

import com.esotericsoftware.kryo.Serializer;

public class HashMapSerializer  extends Serializer {
	public def equals(var that:x10.lang.Any):x10.lang.Boolean {
	  return false;
	}
	
    //@NoThisAccess
	// public def typeName():x10.lang.String {
	//   return null;
	// }
	
	public def toString():x10.lang.String {
	  return null;
	}
	
	public def write(var a1:com.esotericsoftware.kryo.Kryo, var a2:com.esotericsoftware.kryo.io.Output,
	                 var a3:x10.lang.Any):void {
	         
	}
	
	public def hashCode():x10.lang.Int {
	  return 0N;
	}
	
	public def read(var a1:com.esotericsoftware.kryo.Kryo, var a2:com.esotericsoftware.kryo.io.Input,
	                var a3:java.lang.Class):x10.lang.Any {
	  return null;
	}
}