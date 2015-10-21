/**
 * Copyright 2015 Acacia Team

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.acacia.localstore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringBuilder;

public class AcaciaLocalStoreCatalogManager{

 	private static def loadCatalog(baseDir:String ):File {
 		file:File = new File(baseDir + File.separator + "catalog");
		
		try{
			if(!file.exists()){
				file.createNewFile();
 			}
		}catch(e:IOException){
			e.printStackTrace();
		}

 		return file;
 	}
 	
 	public static def writeCatalogRecord(catalogFilePath:String , key:String, value:String):Boolean{
 		var result:String = null;
 		val sb:StringBuilder = new StringBuilder();

		try{
 			reader:BufferedReader = new BufferedReader(new FileReader(loadCatalog(catalogFilePath)));
			var line:String = null;
			while((line = reader.readLine()) != null){
				if(line.indexOf(key) >= 0){
					result = line.split(":")(1n);
				break;
				}else{
					sb.append(line);
				}
			}
			reader.close();
		}catch(ec:IOException){
			ec.printStackTrace();
		return false;
		}
		
		if((result != null)&& (result.equals(value))){
			return true;
		}else{
			sb.append(key + ":" + value);
		}
		
		var fw:FileWriter;
		try {
			fw = new FileWriter(catalogFilePath);
			bw:BufferedWriter = new BufferedWriter(fw);
			bw.write(sb.toString());
			bw.close();
		} catch (e:IOException) {
			e.printStackTrace();
			return false;
		}

 		return true;
 	}
 
	public static def readCatalogRecord(catalogFilePath:String , key:String ):String {
	var result:String  = null;
	try{
		reader:BufferedReader  = new BufferedReader(new FileReader(loadCatalog(catalogFilePath)));
		var line:String  = null;
		while((line = reader.readLine()) != null){
		if(line.indexOf(key) >= 0){
		result = line.split(":")(1n);
		break;
		}
		}
	}catch(ec:IOException){
		ec.printStackTrace();
	}
	
	return result;
	}
}