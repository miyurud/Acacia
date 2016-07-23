/**
 * Copyright 2016 Acacia Team

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

package org.acacia.partitioner.stream;

import x10.compiler.Native;
import x10.array.Array;
import x10.util.HashMap;
import x10.util.ArrayList;
import x10.util.Random;

import org.acacia.util.Utils;
import org.acacia.util.PlaceToNodeMapper;

/**
 * This class contains methods to partition the streaming graph
 * using Linear Deterministic Greedy Algorithm
 */
public class LDGPartitioner {
    private var placesList: ArrayList[Long];
    private var hosts:Rail[String] = null;
    
    public def this(){
        // get the list of available hosts
        hosts = org.acacia.util.Utils.getPrivateHostList();
    }

    /**
     * randomly select a place from the list of places for a given host
     */
    public def selectPlaceToStoreEntity():Long{
        //select the approapriate host using LDG
        var selectedHost:String = selectLDGHost();

        //get list of places of the selected host
        getAllPlacesPerHost(selectedHost);

        random:Random = new Random();
        size: Int = Int.parse(placesList.size()-1+"");
        number:Int = random.nextInt(size) ;

        //select the place randomly
        selectedPlaceId:Long = placesList.get(number);

        Console.OUT.println("selected place " + selectedPlaceId);

        return selectedPlaceId;
    }



    /**
     * select the host with minimum free space according to LDG 
     */
    public def selectLDGHost():String{
        var LDGValue:Double = -1d;
        var weight:Double = 0d;
        var maxLDGValue:Double = -1d;
        var selectedHost:String = null;

        for(host in hosts){
            //get free space information for each host
            val freeSP = call_getFreeSpaceInfo(host);

            //extract the free space percentage from the returned string
            var freeSPComponents:Rail[String] = freeSP.split(":");
            Console.OUT.println(" Percentage of space used " + freeSPComponents(3));

            val usedSpace = x10.lang.Double.parse(freeSPComponents(3));

            //calculate the penalty for the partition
            weight = addPenalty(usedSpace);

            Console.OUT.println("calculated weight "+ weight);

            LDGValue=weight*(100-usedSpace);

            Console.OUT.println("LDG Value" +LDGValue);

            if(maxLDGValue<=LDGValue){
                maxLDGValue = LDGValue; 
                selectedHost=host; // select this host as the candidate to allocate the node
            }
        }

        return selectedHost;
    }

    /**
     * weight each partition by a penalty function
     * w(t,i) =1 - |P(ti)|/C
     * |P(ti)| represents number of edges in chosen partition. In this case we consider freespace
     * C is a pre-defined constant 
     */

    private static def addPenalty(val numericFreeSP:Double):Double{
        val C = 10000000; //MB
        var weight:Double=0d;
        weight = 1 - numericFreeSP/C;
        return weight;
    }



    /**
     * Create a list of places for the chosen host
     */
    private def getAllPlacesPerHost(val selectedHost:String){
        placesList = new ArrayList[Long]();

        for (p in Place.places()){
            if(PlaceToNodeMapper.getHost(p.id).equals(selectedHost)){
                placesList.add(p.id);
            }   
        }

        Console.OUT.println("list of places " + placesList);
    }


    @Native("java", "org.acacia.server.AcaciaManager.getFreeSpaceInfo(#1)")
    static native def call_getFreeSpaceInfo(String):String;
}