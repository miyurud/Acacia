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

#!/bin/bash

APPDIR=/home/miyurud/software/x10dt-2.5.6/workspace/Acacia

ls $APPDIR/lib

java -classpath .:$APPDIR/log4j.properties:$APPDIR/x10-lib/x10.jar:$APPDIR/build:$APPDIR/lib/kryo-shaded-3.0.2-SNAPSHOT.jar:$APPDIR/build/classes:$APPDIR/lib/j2ee-1.4.jar:$APPDIR/lib/minlog-1.3.1-SNAPSHOT.jar:$APPDIR/lib/kryo-3.0.2-SNAPSHOT.jar:$APPDIR/lib/objenesis-2.2-SNAPSHOT.jar:$APPDIR/lib/inject-api-1.0.0-PRD.jar:$APPDIR/lib/cdi-api-1.0.jar:$APPDIR/lib/commons-httpclient-3.1.jar:$APPDIR/lib/commons-codec-1.4.jar:$APPDIR/lib/commons-daemon-1.0.11.jar:$APPDIR/lib/jackson-core-asl-1.9.7.jar:$APPDIR/lib/jackson-mapper-asl-1.8.8.jar:$APPDIR/lib/jamon-runtime-2.3.1.jar:$APPDIR/lib/libthrift-0.8.0.jar:$APPDIR/lib/geronimo-jta.jar:$APPDIR/lib/metrics-core-2.1.2.jar:$APPDIR/lib/jersey-server-1.9.jar:$APPDIR/lib/jersey-core-1.9.jar:$APPDIR/lib/asm-3.2.jar:$APPDIR/lib/servlet-api-2.5-20081211.jar:$APPDIR/lib/guava-11.0.2.jar:$APPDIR/lib/jetty-6.1.25.jar:$APPDIR/lib/jetty-util-6.1.25.jar:$APPDIR/lib/jettison-1.3.jar:$APPDIR/lib/slf4j-log4j12-1.7.2.jar:$APPDIR/lib/slf4j-api-1.7.2.jar:$APPDIR/lib/log4j-1.2.16.jar:$APPDIR/lib/protobuf-java-2.4.0a.jar:$APPDIR/lib/commons-collections-3.2.1.jar:$APPDIR/lib/zookeeper-3.4.5.jar:$APPDIR/lib/hsqldb-2.2.9.jar:$APPDIR/lib/hadoop-core-1.0.4.jar:$APPDIR/lib/hbase-0.94.4.jar:$APPDIR/lib/commons-configuration-1.6.jar:$APPDIR/lib/commons-cli-1.2.jar:$APPDIR/lib/commons-io-1.4.jar:$APPDIR/lib/commons-lang-2.5.jar:$APPDIR/lib/commons-logging-1.1.1.jar:$APPDIR/neo4j-lib/geronimo-jta_1.1_spec-1.1.1.jar:$APPDIR/neo4j-lib/lucene-core-3.5.0.jar:$APPDIR/neo4j-lib/neo4j-cypher-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-graph-algo-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-graph-matching-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-jmx-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-kernel-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-lucene-index-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-shell-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-udc-1.8.1.jar:$APPDIR/neo4j-lib/org.apache.servicemix.bundles.jline-0.9.94_1.jar:$APPDIR/neo4j-lib/scala-library-2.9.1-1.jar:$APPDIR/neo4j-lib/server-api-1.8.1.jar test.acacia.localstore.TestAcaciaLocalStore
