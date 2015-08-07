package org.acacia.rdf.sparql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.acacia.localstore.java.AcaciaHashMapNativeStore;
import org.acacia.util.java.Utils_Java;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;

public class ExecuteQuery {
	
	HashMap<String, String> Prefix= new HashMap<String, String>();		

	public ArrayList<String> executeQuery(String query, String graphID, String partitionID) {

		ANTLRStringStream stream = new ANTLRStringStream(query);
		SparqlLexer lexer = new SparqlLexer(stream);
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		SparqlParser parser = new SparqlParser(tokenStream);
		int index;
		ArrayList<String> result = null;
				
			try {
				parser.query();
			
				// extract triples from the query
				String[] triples = extractTriples(query);
				// get the data set
				ArrayList<String> graphData = new ArrayList<String>();
				graphData = loadData(graphID, partitionID);
	/*
				index = query.indexOf("SELECT");
	
				if (index >= 0) {
					// get the results of select query
					result = selectQuery(graphData, triples);
				}*/
			}catch (org.antlr.runtime.RecognitionException e) {
				e.printStackTrace();
			}
		return result;

	}

	// read nodes,relationship,properties
	public String[] extractTriples(String query){

		 	 
		 int index,index2;
		 String[] prefix;
		 String key,value;
		String[] triples;
		
		 
		 prefix=query.substring(0,query.indexOf("SELECT")).trim().split("PREFIX");
		 		 
		 for(int i=0; i < prefix.length; i++){
		 
		 if(prefix[i].length()>0){
			 key=prefix[i].trim().substring(0,prefix[i].indexOf(":")-1).trim();
			 value=prefix[i].trim().substring(prefix[i].indexOf("<"),prefix[i].indexOf(">")-1).trim();
			 Prefix.put(key,value);
		 }
			 
		 }
		 index=query.indexOf("{");  // simple query
		 index2=query.indexOf("}");		 
		 
		
		 // ; also should be considered 
		 triples=query.substring(index+1,index2).trim().split(" .");  		 
		 return triples;
	 }

	// load data
	public ArrayList<String> loadData(String graphID, String partitionID) {

		ArrayList<String> data = new ArrayList<String>();
		String baseDir = Utils_Java.getAcaciaProperty("org.acacia.server.instance.datafolder");

		// /var/tmp/acad-localstore
		AcaciaHashMapNativeStore nativeStore = new AcaciaHashMapNativeStore(Integer.parseInt(graphID), Integer.parseInt(partitionID),baseDir,false);
		nativeStore.loadGraph();

		HashMap<Long, HashSet<Long>> localSubGraphMap = nativeStore.getlocalSubGraphMap();
		HashMap<Long, HashSet<String>> vertexPropertyMap= nativeStore.getvertexPropertyMap();
		HashMap[] relationshipMapWithProperties=nativeStore.getrelationshipMapWithProperties();
		HashMap<Long, HashMap<Integer, HashSet<String>>> attributeMap=nativeStore.getattributeMap();
		HashMap<Integer, String> predicateStore=nativeStore.getpredicateStore();
		
		
		//graph data should be aggregated and added to 'data'

		return data;

	}
	
	// this is a test class for executing query
		public ArrayList<String> selectQuery(ArrayList<String> graphData, String[] triples){

			
	 		 ArrayList<ArrayList<String>> intermediateResults=new ArrayList<ArrayList<String>>(); 
	         ArrayList<String> Results= new ArrayList<String>();  			
	 		 //ArrayList<String> variables = new ArrayList<String>(); 
	 		 int n=0;
	 				
			intermediateResults= execute(triples, graphData);
			
			//AND results
			//considering only one variable  (?x - -)

			for(int i=0; i < intermediateResults.get(n).size(); i++){

				String[] arr1=intermediateResults.get(n).get(i).trim().split(",");

				for(int j=0; j < intermediateResults.get(n+1).size(); j++){
					
					String[] arr2=intermediateResults.get(n+1).get(j).trim().split(",");

					if(arr1[0].equals(arr2[0])){

						if(!Results.contains(arr1[0])){
	                        Results.add(arr1[0]);
	               			
						}
					}
	              }								
			}
	 		
		return Results;
		
		}


	public ArrayList<ArrayList<String>>  execute(String[] triples, ArrayList<String> graphData) {

		ArrayList<ArrayList<String>> intermediateResults = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < triples.length; i++) {

			// get the tokens of the triples
			String[] tokens = triples[i].trim().split(" ");

			// predicate

			tokens[1] = Prefix.get(tokens[1].substring(0,
					tokens[1].indexOf(":")).trim())
					+ tokens[1].substring(tokens[1].indexOf(":") + 1).trim();

			if (tokens[0].indexOf("?") >= 0) {
				if (tokens[2].indexOf("?") >= 0) {
					 intermediateResults.add(triplePattern1(tokens,graphData));
				} else {

					if (!tokens[2].startsWith("<http")) {
						tokens[2] = Prefix.get(tokens[2].substring(0,
								tokens[2].indexOf(":")).trim())
								+ tokens[2].substring(
										tokens[2].indexOf(":") + 1).trim();
					} else {
						tokens[2] = tokens[2].substring(1, tokens[2].length());
					}

					 intermediateResults.add(triplePattern2(tokens,graphData));
				}
			} else {
				tokens[0] = Prefix.get(tokens[0].substring(0,
						tokens[0].indexOf(":")).trim())
						+ tokens[0].substring(tokens[0].indexOf(":") + 1)
								.trim();
				if (tokens[2].indexOf("?") >= 0) {
					 intermediateResults.add(triplePattern3(tokens,graphData));
				} else {
					tokens[2] = Prefix.get(tokens[2].substring(0,
							tokens[2].indexOf(":")).trim())
							+ tokens[2].substring(tokens[2].indexOf(":") + 1)
									.trim();
					 intermediateResults.add(triplePattern4(tokens,graphData));
				}
			}
			

		}
		return intermediateResults;

	}

	// (?X , name, ?N)
	public ArrayList<String> triplePattern1(String[] tokens,
			ArrayList<String> graphData) {

		ArrayList<String> temp = new ArrayList<String>();
		int k = 0;

		// only predicate should be considered

		for (int i = 0; i < graphData.size(); i++) {

			if (graphData.get(i).indexOf(tokens[1].substring(3)) >= 0) {

				temp.set(k, graphData.get(i));
				k = k + 1;
			}
		}
		return temp;

	}

	// (?X , name, "John")
	public ArrayList<String> triplePattern2(String[] tokens,
			ArrayList<String> graphData) {

		ArrayList<String> temp = new ArrayList<String>();
		int k = 0;

		// predicate and object should be considered

		for (int i = 0; i < graphData.size(); i++) {

			if (graphData.get(i).indexOf(tokens[1].substring(3)) >= 0
					&& graphData.get(i).indexOf(
							tokens[2].substring(1, tokens[2].length() - 1)) >= 0) {

				temp.set(k, graphData.get(i));
				k = k + 1;
			}

		}
		return temp;

	}

	// (d:100 , name, ?N)
	public ArrayList<String> triplePattern3(String[] tokens,
			ArrayList<String> graphData) {

		ArrayList<String> temp = new ArrayList<String>();
		int k = 0;

		// subject and predicate should be considered

		for (int i = 0; i < graphData.size(); i++) {

			if (graphData.get(i).indexOf(tokens[0]) >= 0
					&& graphData.get(i).indexOf(tokens[1].substring(3)) >= 0) {

				temp.set(k, graphData.get(i));
				k = k + 1;
			}

		}
		return temp;
	}

	// (d:100 , name, "John")
	public ArrayList<String> triplePattern4(String[] tokens,
			ArrayList<String> graphData) {

		ArrayList<String> temp = new ArrayList<String>();
		int k = 0;

		// subject, predicate and object should be considered

		for (int i = 0; i < graphData.size(); i++) {

			if (graphData.get(i).indexOf(tokens[0]) >= 0
					&& graphData.get(i).indexOf(tokens[1].substring(3)) >= 0
					&& graphData.get(i).indexOf(
							tokens[2].substring(1, tokens[2].length() - 1)) >= 0) {

				temp.set(k, graphData.get(i));
				k = k + 1;
			}

		}
		return temp;
	}

}
