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

package org.acacia.server;

/**
 * Class AcaciaInstanceCatalogManager
 */
public class AcaciaInstanceCatalogManager {
//Note on Feb 15 2015: This was kept aside for a while since it is not too urgent to create a class for managing the catalog file

// //       private def writeCatalogRecord(String record) {
// //First we need to check whether the Acacia's data directory exists or not.
// File fileChk = new File(dataFolder);
// if(fileChk.exists() && fileChk.isDirectory()){
// //There is no problem
// }else{
// //Here we need to create the directory first
// try{
// org.apache.commons.io.FileUtils.forceMkdir(fileChk);
// }catch(IOException ex){
// Logger_Java.error("Creating the Acacia data folder threw an Exception : " + ex.getMessage());
// }
// }				
// 
// File catalog = new File(dataFolder + "/catalog");
// boolean b = false;
// try{
// if(!catalog.exists()){
// b = catalog.createNewFile();
// }
// 
// if(b){
// System.out.println("The catalog file was newly created.");
// }
// 
// BufferedWriter writer = new BufferedWriter(new FileWriter(catalog, true));//We are appending to the file rather than replacing
// writer.write(record);
// writer.write("\n");//We need a new line to separate between two records.
// writer.flush();
// writer.close();
// }catch(IOException e){
// System.out.println("There is an error is writing to the AcaciaInstance's catalog.");
// }
// }
}