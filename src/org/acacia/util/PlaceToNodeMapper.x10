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