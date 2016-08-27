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

package test.acacia.partitioner.local.java;

import org.acacia.partitioner.local.java.MetisPartitioner;

public class TestMetisFormatConverter {
    public static void main(String[] args) {
        MetisPartitioner converter = new MetisPartitioner();
        // converter.convert("/home/miyurud/Acacia/graphs/simple_graph.dl");
        // converter.convert("/home/miyurud/Acacia/graphs/simple_graph-no-header.dl");
        // /home/miyurud/Acacia/graphs/powergrid.dl
        boolean isDistrbutedCentralPartitions = false;
        // converter.convert("graphname",
        // "/home/miyurud/Acacia/graphs/powergrid.dl", "/home/miyurud/tmp", 4,
        // isDistrbutedCentralPartitions);
        // converter.printGraphContent();
    }
}
