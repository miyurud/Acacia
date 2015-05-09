package org.acacia.frontend;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.Bindings;

import org.acacia.tinkerpop.blueprints.impl.AcaciaGraph;

/*
public class AcaciaGremlinInterpreter {
public def this(val b:BufferedReader, val o:PrintWriter){

}

public def run(){
var manager:javax.script.ScriptEngineManager = new javax.script.ScriptEngineManager(); 
}
}*/

public class AcaciaGremlinInterpreter {
    var buff:BufferedReader = null;
    var out:PrintWriter = null;
    
	public def this(){
	
	}

	public def this(val b:BufferedReader, val o:PrintWriter){
    	buff = b;
    	out = o;
        //run();//Not very good approach though to call run() here...
	}

    public def run(){
	    var msg:String = null;
	    
	    try{
	    	msg = buff.readLine();
	    
	    	while(!msg.equals(AcaciaFrontEndProtocol.EXIT)){
	    		
			    out.println("abc");
			    out.flush();
			    msg = buff.readLine();
	    	}
	    
	    out.println("exit : " + msg);
	    msg = buff.readLine();
	    out.println("exit : " + msg);
	    out.flush();
	    
	    //var manager:javax.script.ScriptEngineManager = new javax.script.ScriptEngineManager(); 
	    //var engine:javax.script.ScriptEngine = manager.getEngineByName("gremlin-groovy");
	    var engine:javax.script.ScriptEngine = new com.tinkerpop.gremlin.groovy.jsr223.GremlinGroovyScriptEngineFactory().getScriptEngine(); 
	    
	    if (engine == null){
	    out.println("Could not get the engine");
	    out.flush();
	    }
	    
	    var bindings:Bindings = engine.createBindings();
	    var graph:AcaciaGraph = new AcaciaGraph();
	    graph.addVertex("v1");
	    bindings.put("g", graph);
	    //msg = "" + engine.eval("i='hello'");
	    msg = "" + engine.eval("g.V[1].name", bindings);
	    out.println("uuu : " + msg);
	    out.flush();
	    
	    }catch(var e:IOException){
	    	//e.printStackTrace();
	    out.println(e.getMessage());
	    out.flush();
	    }catch(var ex:ScriptException){
	    	//ex.printStackTrace();
	    out.println(ex.getMessage());
	    out.flush();
	    }
	}
}