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
import org.acacia.util.Utils;
import org.acacia.util.PlaceToNodeMapper;
import x10.array.Array;
import x10.util.HashMap;
import x10.util.ArrayList;
import x10.util.Random;
import x10.io.FileReader;
import x10.io.File;
import x10.io.Printer;
import x10.io.IOException;

import org.acacia.server.AcaciaServer;
import org.acacia.server.AcaciaManager;
import org.acacia.server.GraphStatus;
import org.acacia.centralstore.AcaciaHashMapCentralStore;
import org.acacia.metadata.db.java.MetaDataDBInterface;
import org.acacia.util.java.Utils_Java;

public class StreamingLDGPartitioner {
     private var maximum_node:Int= -1n;
     private var partitionList:ArrayList[Rail[Int]];
     private var cutRatioList:ArrayList[Float];
    /**
     * If Input edge list is pre-processed such that 
     * all edges with starting with a particular node come together as a batch
     * i.e as
     * ["2 3", "2 4", "2 8" , "7 5", "7 6"....]
     * and not randomly as
     * ["2 3", "7 5", "2 4", "2 8", "7 6"]
     * then quality of partitioning will be higher
     */
    
    public def StreamingLDG(var edges:ArrayList[String],var partition_count:Int, var partitions:Rail[Int] ):Rail[Int]{
            
        var UNASSIGNED:Int=-1n;
       
        if(partitions==null){  
           partitions = new Rail[Int](maximum_node+1n,-1n);
        }
        if(cutRatioList==null){
           cutRatioList=new ArrayList[Float]();
        }
        if(partitionList==null){
           partitionList= new ArrayList[Rail[Int]]();
        }
        var partition_sizes:Rail[Int] = new Rail[Int](partition_count,0n);
      
        /**
         * Fill partition sizes based on partition assignments in previous iteration
         */
        for(p in partitions){
            if(p==UNASSIGNED){
                continue;
            }
            partition_sizes(p)++; 
        }
        
        var partition_votes:Rail[Int] = new Rail[Int](partition_count,0n);
        
        var maximum_capacity:Int  = (edges.size() as Int) /partition_count;
        
        /**
         * Initially extract the first node of the Edge array
         */
        var previous_left_node:Int = Int.parse(edges(0).split(" ")(0));
        var left_node:Int=0n;
        var right_node:Int=0n;
        var max_partition_position:Int=0n;
        var partition_position:Int=0n;
        var max_LDG_val:Int=0n;
        var LDG_val:Int=0n;
        var num_edges:Int=edges.size() as Int ; 
        
        for(var i:Int=0n; i<num_edges; i++){
            
            val nodes = edges(i).split(" ");
            left_node=Int.parse(nodes(0));
            right_node=Int.parse(nodes(1));
         
            if(previous_left_node!=left_node){
                /**
                 * A new node has been found. So we have to assign previous_left_node to 
                 * a partition;
                 */ 
                max_partition_position=0n;
                max_LDG_val=(partition_votes(0))*(maximum_capacity-partition_sizes(0));
                
                for(var tmp_partition:Int = 0n; tmp_partition<partition_count; tmp_partition++){
                    
                    LDG_val = (partition_votes(tmp_partition))*(maximum_capacity-partition_sizes(tmp_partition));
                    
                    if(LDG_val>max_LDG_val){
                        max_partition_position=tmp_partition;
                        max_LDG_val=LDG_val;
                    }    
                 }
                
                 if(max_LDG_val==0n){ 
                    /**
                     * use balanced partitioning. Break ties randomly
                     */
                    max_partition_position = partitionUsingBalanced(partition_sizes,maximum_capacity);    
                   
                 }  
                 
                 
                 partition_sizes(max_partition_position)++;
                 
                
                /**
                 * make previous partition available to another node
                 */ 
                 if(partitions(previous_left_node)!=UNASSIGNED){
                     if (partition_sizes(partitions(previous_left_node))>0){
                         partition_sizes(partitions(previous_left_node))--;
                     }
                  }
                 
                 partitions(previous_left_node)=max_partition_position;
                 partition_votes.fill(0n);
                 previous_left_node= left_node;
             
             }    
             if(partitions(right_node)!=UNASSIGNED){
                 partition_votes(partitions(right_node))++;
             } 
            
        }
         
        /**
         * Last node is still remaining without a partition.
         * Assign that to a partition
         */
        max_partition_position=0n;
        max_LDG_val=0n;
        for(var tmp_partition:Int=0n; tmp_partition<partition_count;tmp_partition++){
            if(partition_sizes(tmp_partition) < maximum_capacity){
                LDG_val	=(partition_votes(tmp_partition))*(1n-(partition_sizes(tmp_partition)/maximum_capacity));
               
                if(LDG_val>max_LDG_val){
                    max_partition_position=tmp_partition;
                    max_LDG_val = LDG_val;
                }
            }
        }
       
        partitions(left_node)=max_partition_position;
        partition_sizes(max_partition_position)++;
       
         
        var member_count:Rail[Int] = new Rail[Int](partition_count); 
        member_count.fill(0n);
        
        for(p in partitions){
           if(p==UNASSIGNED){
               continue;
           }
            member_count(p)++; 
        }
        Console.OUT.print("\t\t" + member_count.toString());
        
        var assigned_nodes:Int =0n;
        
        for(n in member_count){
            assigned_nodes=assigned_nodes+n;
        }
        
        
        calculate_edges_per_partition(partitions,edges,partition_count);
        partitionList.add(partitions);
        return partitions;  
    
    }
            
    public  def calculate_edges_per_partition(var partitions:Rail[Int], var edges:ArrayList[String], var partition_count:Int){
        var left_node:Int = 0n;
        var right_node:Int = 0n;
        var edge_count:Rail[Int] = new Rail[Int](partition_count);
        edge_count.fill(0n);
       
        var crossing_edges:Int=0n;
        for(var i:Int=0n; i<edges.size(); i++){
            val nodes = edges(i).split(" ");
            left_node=Int.parse(nodes(0));
            right_node=Int.parse(nodes(1));
           
            if(partitions(left_node) == partitions(right_node)){
                // two nodes are in the same partition
                edge_count(partitions(left_node))++;
            }
            else{
                crossing_edges++;
               // edge_count(partition_count)++;
            }
            
         }
        Console.OUT.print("\t\t" + edge_count.toString());
        Console.OUT.print("\t\t" + crossing_edges);
        var cut_ratio:Float = crossing_edges/edges.size() as Float;
        cutRatioList.add(cut_ratio);
        Console.OUT.print("\t\t" + cut_ratio);
    }
    
    public def getPartitionWithMinimumCutRatio():Rail[Int]{
        var minCutRatio:Float= 1.0 as Float;
        var minCutIndex:Int = -1n;
        var partitionWithMinimumCutRatio:Rail[Int];
        for( var i:Int=0n; i<cutRatioList.size(); i++){
            if(cutRatioList(i)<minCutRatio){
                minCutRatio = cutRatioList(i);
                minCutIndex=i;
            }
        }
        Console.OUT.println("min Cut Ratio " +minCutRatio);
        Console.OUT.println("min Cut Partition " +minCutIndex);
        
        partitionWithMinimumCutRatio = partitionList.get(minCutIndex);
        cutRatioList.clear();
        partitionList.clear();
        return partitionWithMinimumCutRatio;
        
    }
    
    private static def partitionUsingBalanced(var partition_sizes:Rail[Int], var maximum_capacity:Int):Int{
       var  max_partition_position:Int=-1n;
       var min:Int = partition_sizes(0);
      // Console.OUT.println("partition sizes " + partition_sizes.toString());
       for(partition_size in partition_sizes){
           if(partition_size<min){
               min=partition_size;
               //Console.OUT.println("min partition " + min);
           }
       } 
       //look for further occuraces of min
       var min_count:Int = 0n;
       var min_count_partitions:ArrayList[Int]= new ArrayList[Int]();      
       for(var k:Int = 0n ; k<partition_sizes.size; k++){
           if(partition_sizes(k)==min){
               min_count++;
               min_count_partitions.add(k);
            }
        }
       
        if(min_count>0){
            random:Random = new Random();
            var number:Int = random.nextInt(min_count_partitions.size() as Int) ;
            if(partition_sizes(min_count_partitions(number))<maximum_capacity){
                max_partition_position=min_count_partitions(number);
                //Console.OUT.println(" generated random partition pos " + max_partition_position);
             }
        }
        else{
            if(min_count_partitions.get(0)<maximum_capacity){
                max_partition_position=min_count_partitions.get(0);
            }   
        }
       
        return max_partition_position; 
    }
            
   
    
    public def generateDirectedGraph(var lines : ArrayList[String]):ArrayList[String]{
        
        var map:HashMap[String, ArrayList[String]]  = new HashMap[String, ArrayList[String]]();
        var edge:Rail[String] = new Rail[String](2);
        var direct_edge:String;
        var reverse_edge:String;
        var count:Int = 1n;
        maximum_node=0n;
        
        for(line in lines){
            edge=line.split(" ");
            var left_node:Int = Int.parse(edge(0));
            var right_node:Int = Int.parse(edge(1));
            if(left_node>maximum_node){
               maximum_node=left_node;
            }
            if(right_node>maximum_node){
               maximum_node=right_node;
            }
            if (map.containsKey(edge(1))) {
                // get that value and add new edge too
                map.get(edge(1)).add(edge(0));
            } else {
                var list:ArrayList[String] = new ArrayList[String]();
                list.add(edge(0));
                map.put(edge(1), list);
            }
        }
        for (entry in map.entries()){
            var key:String = entry.getKey();
            var values:ArrayList[String] = entry.getValue();
            for(value in values){
                lines.add(key + " " +value);
            }
        }
        
        return lines;
        
        
    }
    
    public static def saveEdgesToDisk(var partitions:Rail[Int], var edges:ArrayList[String], var graphID:String){
        //iterate over the edges list
       val centralStoresMap:HashMap[Short, AcaciaHashMapCentralStore] = new HashMap[Short, AcaciaHashMapCentralStore](); 
       
       val numberOfCentralPartitions:int = 4n;	
       val hosts= org.acacia.util.Utils.getPrivateHostList();
       

       for(var i:short = 0s; i < numberOfCentralPartitions; i++){
       		centralStoresMap.put(i, new AcaciaHashMapCentralStore(Int.parseInt(graphID), i));
       }
       
       for(edge in edges){
            
        	val nodes = edge.split(" ");
        	val left_node=Int.parse(nodes(0));
        	val right_node=Int.parse(nodes(1));
          
            if(partitions(left_node)==partitions(right_node)){
               //This edge belongs to the same partition
               //"mahen-Satellite-C50-A" below here is for testing purposes  only. Correct host will be retrieved via val hosts Rail[String] above;
               AcaciaServer.insertEdge("mahen-Satellite-C50-A", Long.parse(graphID), Long.parse(left_node+""), Long.parse(right_node+""));
            }
            
            else{
                //save this edge to the central store of the from vertex
                centralStoresMap.get(partitions(left_node) as short).addEdge(left_node, right_node);
                //central.storeGraph();
            }
        }
       
        //save all the edges in each central store
        for(var i:short = 0s; i < numberOfCentralPartitions; i++){
        		centralStoresMap.get(i).storeGraph();
        }
       
        
    }
    
    
 
    
   
   
}