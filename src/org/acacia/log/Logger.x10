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

package org.acacia.log;

import x10.compiler.Native;

public class Logger {

	public static def trace(val message:String){
		call_trace(message);
	}
	
	public static def debug(val message:String){
		call_debug(message);
	}
	
	public static def info(val message:String){
		call_info(message);
	}
	
	public static def warn(val message:String){
		call_warn(message);
	}
	
	public static def error(val message:String){
		call_error(message);
	}
	
	public static def fatal(val message:String){
		call_fatal(message);
	}	
	
	
	@Native("java", "org.acacia.log.java.Logger_Java.trace(#1)")
	static native def call_trace(String):void;
	
	@Native("java", "org.acacia.log.java.Logger_Java.debug(#1)")
	static native def call_debug(String):void;
	
	@Native("java", "org.acacia.log.java.Logger_Java.info(#1)")
	static native def call_info(String):void;
	
	@Native("java", "org.acacia.log.java.Logger_Java.warn(#1)")
	static native def call_warn(String):void;
	
	@Native("java", "org.acacia.log.java.Logger_Java.error(#1)")
	static native def call_error(String):void;
	
	@Native("java", "org.acacia.log.java.Logger_Java.fatal(#1)")
	static native def call_fatal(String):void;
}