###
# Copyright 2015 Acacia Team
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
##

APPDIR=/opt/Acacia

x10cj -cp build/classes:lib/log4j-1.2.16.jar:lib/blueprints-core-2.4.0.jar:lib-gremlin-groovy/gremlin-groovy-2.4.0.jar:lib/hadoop-core-1.0.4.jar:lib/commons-io-1.4.jar:lib/antlr-runtime-3.5-sources.jar:lib/antlr-complete-3.5.2.jar:lib/guava-11.0.2.jar:lib/kafka_2.11-0.10.0.0.jar:lib/scala-library-2.11.8.jar:lib/scala-parser-combinators_2.11-1.0.4.jar\
 src/org/acacia/log/java/Logger_Java.java\
 src/org/acacia/util/java/Conts_Java.java\
 src/org/acacia/util/java/Utils_Java.java\
 src/org/acacia/util/java/KafkaConsumer.java\
 src/org/acacia/vertexcounter/java/VertexCounter.java\
 src/org/acacia/vertexcounter/java/VertexCounterClient.java\
 src/org/acacia/vertexcounter/java/VertexCounterProtocol.java\
 src/org/acacia/vertexcounter/java/VertexCounterServiceSession.java\
 src/org/acacia/vertexcounter/java/VertexSingleton.java\
 src/org/acacia/tinkerpop/blueprints/impl/java/AcaciaEdge.java\
 src/org/acacia/tinkerpop/blueprints/impl/java/AcaciaGraphQuery.java\
 src/org/acacia/tinkerpop/blueprints/impl/java/AcaciaPredicate.java\
 src/org/acacia/tinkerpop/blueprints/impl/java/AcaciaVertex.java\
 src/org/acacia/metadata/db/java/AcaciaHSQLDBComm.java\
 src/org/acacia/metadata/db/java/MetaDataDBInterface.java\
 src/org/acacia/rdf/sparql/java/SparqlLexer.java\
 src/org/acacia/rdf/sparql/java/SparqlParser.java\
 src/org/acacia/server/java/AcaciaInstanceProtocol.java\
 src/org/acacia/server/java/AcaciaBackEndProtocol.java\
 src/org/acacia/events/java/ShutdownEvent.java\
 src/org/acacia/events/java/DBTruncateEventListener.java\
 src/org/acacia/events/java/DBTruncateEvent.java\
 src/org/acacia/events/java/ShutdownEventListener.java\
 src/org/acacia/server/java/AcaciaInstanceFileTransferServiceSession.java\
 src/org/acacia/server/java/AcaciaInstanceFileTransferService.java\
 src/org/acacia/partitioner/index/PartitionIndexObject.java\
 src/org/acacia/partitioner/index/PartitionIndexProtocol.java\
 src/org/acacia/partitioner/index/PartitionIndexServiceSession.java\
 src/org/acacia/partitioner/index/PartitionIndex.java\
 src/test/acacia/partitioner/index/TestPartitionIndex.java\
 src/org/acacia/csr/java/Utils.java\
 src/org/acacia/centralstore/java/HSQLDBInterface.java\
 src/org/acacia/partitioner/hadoop/java/HDFSFile_Java.java\
 src/org/acacia/partitioner/index/PartitionIndexClient.java\
 src/org/acacia/query/algorithms/degree/OutDegree.java\
 -d $APPDIR/build/classes
