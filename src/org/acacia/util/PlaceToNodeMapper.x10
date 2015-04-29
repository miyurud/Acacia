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

package org.acacia.util;

/**
 * Class PlaceToNodeMapper acts as the facade that provides the access to the details of mapping of each
 * place id to host and port
 */
public class PlaceToNodeMapper {

  public static def getHost(val placeID:Long):String{
    var result:String = null;
    val nPlaces = Place.places().size() as Int;
    val hostList:Rail[String] = Utils.getPrivateHostList();
    //val placesPerHost = nPlaces/hostList.size;
    val hostID = placeID % hostList.size;
    
    result = hostList(hostID); 
    
    return result;
  }
  
  public static def getInstancePort(val placeID:Long):Int{
    var port:Int = org.acacia.util.java.Conts_Java.ACACIA_INSTANCE_PORT;//This is the starting point
    val nPlaces = Place.places().size() as Int;
    val hostList:Rail[String] = Utils.getPrivateHostList();
    //val placesPerHost:Int = (nPlaces/hostList.size) as Int;
    val hostCount = hostList.size as Int;
    val hostID = placeID % hostCount;
    val withinPlaceIndex:Int = ((placeID - hostID) as Int)/hostCount;
    
    return (port + withinPlaceIndex);
  }
  
  public static def getFileTransferServicePort(val placeID:Long):Int{
	  val nPlaces = Place.places().size() as Int;
	  val hostList:Rail[String] = Utils.getPrivateHostList();
	  //val placesPerHost:Int = (nPlaces/hostList.size) as Int;
	  val hostCount = hostList.size as Int;
	  //We basically need to shift the port range by (nPlaces/hostCount) .
	  var port:Int = org.acacia.util.java.Conts_Java.ACACIA_INSTANCE_PORT + (nPlaces/hostCount) ;//This is the starting point for file transfer service
	  val hostID = placeID % hostCount;
	  val withinPlaceIndex:Int = ((placeID - hostID) as Int)/hostCount;
	  
	  return (port + withinPlaceIndex);
  }
}