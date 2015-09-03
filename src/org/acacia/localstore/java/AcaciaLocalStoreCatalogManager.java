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
 
package org.acacia.localstore.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
 
public class AcaciaLocalStoreCatalogManager{
	
	private static File loadCatalog(String baseDir){
		File file = new File(baseDir + File.separator + "catalog");
		//System.out.println("===+++>>" + baseDir + File.separator + "catalog");
		try{
			if(!file.exists()){
				file.createNewFile();
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return file;
	}
	
	 public static boolean writeCatalogRecord(String catalogFilePath, String key, String value){
		String result = null;
		StringBuilder sb = new StringBuilder();

        try{
	        BufferedReader reader = new BufferedReader(new FileReader(loadCatalog(catalogFilePath)));
	        String line = null;
	        while((line = reader.readLine()) != null){
	        	if(line.contains(key)){
	        		result = line.split(":")[1];
	        		break;
	        	}else{
	        		sb.append(line);
	        	}
	        }
	        
	        reader.close();
        }catch(java.io.FileNotFoundException ef){
        	System.out.println("File not found : " + ef.getMessage());
//            File f = new File(catalogFilePath);
//            try {
//				f.createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
        }catch(IOException ec){
        	ec.printStackTrace();
        	return false;
        }
        
        if((result != null)&& (result.equals(value))){
        	return true;
        }else{
        	sb.append(key + ":" + value);
        }
        
		FileWriter fw;
		try {
			fw = new FileWriter(loadCatalog(catalogFilePath));
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sb.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	 }
	 
	 public static String readCatalogRecord(String catalogFilePath, String key){
		String result = null;
        try{
	        BufferedReader reader = new BufferedReader(new FileReader(loadCatalog(catalogFilePath)));
	        String line = null;
	        while((line = reader.readLine()) != null){
	        	if(line.contains(key)){
	        		result = line.split(":")[1];
	        		break;
	        	}
	        }
        }catch(IOException ec){
        	ec.printStackTrace();
        }
        
        return result;
	 }
}