#!/bin/bash

APPDIR=/home/miyurud/software/x10dt/workspace/Acacia

#export X10_HOSTFILE=$1
#export X10_NPLACES=$2
#export X10_NTHREADS=$3
#export X10_STATIC_THREADS=true

X10_HOSTFILE=$APPDIR'/'$1
X10_NPLACES=$2
X10_NTHREADS=$3
X10_STATIC_THREADS=true
#The following line is used to enable/disable the standalone execution of Acacia.
STANDALONE_FLAG=true


#ssh st04 $APPDIR/shutdown-instance.sh &
#ssh st05 $APPDIR/shutdown-instance.sh &
#ssh st06 $APPDIR/shutdown-instance.sh &
#ssh st07 $APPDIR/shutdown-instance.sh &

#sleep 10

#python remove_instances.py

#clean-neo4js.sh &

#sleep 2

#ssh st04 $APPDIR/acacia-instance.sh &
#ssh st05 $APPDIR/acacia-instance.sh &
#ssh st06 $APPDIR/acacia-instance.sh &
#ssh st07 $APPDIR/acacia-instance.sh &
echo '----------------------------------'
echo 'Starting Acacia server - ver 2.0.0'
echo 'Period : April 2015'
echo '----------------------------------'
echo 'Java Home : '$JAVA_HOME
echo 'X10_HOME : '$X10_HOME
echo 'Host file : '$X10_HOSTFILE
echo 'X10 N Places : '$X10_NPLACES
echo 'X10 N threads : '$X10_NTHREADS

#X10_STATIC_THREADS=false X10_NTHREADS=$X10_NTHREADS X10_HOSTFILE=$X10_HOSTFILE X10_NPLACES=$X10_NPLACES x10 -mx8192m -J-DlogFileName=server -J-XX:MaxPermSize=8192m -classpath .:$APPDIR/bin/acacia.jar:$APPDIR/log4j.properties:$APPDIR/build:$APPDIR/build/classes:$APPDIR/lib/j2ee-1.4.jar:$APPDIR/lib/inject-api-1.0.0-PRD.jar:$APPDIR/lib/cdi-api-1.0.jar:$APPDIR/lib/commons-httpclient-3.1.jar:$APPDIR/lib/commons-codec-1.4.jar:$APPDIR/lib/commons-daemon-1.0.11.jar:$APPDIR/lib/jackson-core-asl-1.9.7.jar:$APPDIR/lib/jackson-mapper-asl-1.8.8.jar:$APPDIR/lib/jamon-runtime-2.3.1.jar:$APPDIR/lib/libthrift-0.8.0.jar:$APPDIR/lib/geronimo-jta.jar:$APPDIR/lib/metrics-core-2.1.2.jar:$APPDIR/lib/jersey-server-1.9.jar:$APPDIR/lib/jersey-core-1.9.jar:$APPDIR/lib/asm-3.2.jar:$APPDIR/lib/servlet-api-2.5-20081211.jar:$APPDIR/lib/guava-11.0.2.jar:$APPDIR/lib/jetty-6.1.25.jar:$APPDIR/lib/jetty-util-6.1.25.jar:$APPDIR/lib/jettison-1.3.jar:$APPDIR/lib/slf4j-log4j12-1.7.2.jar:$APPDIR/lib/slf4j-api-1.7.2.jar:$APPDIR/lib/log4j-1.2.16.jar:$APPDIR/lib/protobuf-java-2.4.0a.jar:$APPDIR/lib/commons-collections-3.2.1.jar:$APPDIR/lib/zookeeper-3.4.5.jar:$APPDIR/lib/hsqldb-2.2.9.jar:$APPDIR/lib/hadoop-core-1.0.4.jar:$APPDIR/lib/hbase-0.94.4.jar:$APPDIR/lib/commons-configuration-1.6.jar:$APPDIR/lib/commons-cli-1.2.jar:$APPDIR/lib/commons-io-1.4.jar:$APPDIR/lib/commons-lang-2.5.jar:$APPDIR/lib/commons-logging-1.1.1.jar:$APPDIR/neo4j-lib/geronimo-jta_1.1_spec-1.1.1.jar:$APPDIR/neo4j-lib/lucene-core-3.5.0.jar:$APPDIR/neo4j-lib/neo4j-cypher-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-graph-algo-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-graph-matching-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-jmx-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-kernel-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-lucene-index-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-shell-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-udc-1.8.1.jar:$APPDIR/neo4j-lib/org.apache.servicemix.bundles.jline-0.9.94_1.jar:$APPDIR/neo4j-lib/scala-library-2.9.1-1.jar:$APPDIR/neo4j-lib/server-api-1.8.1.jar::$APPDIR/neo4j-lib/batch-import-jar-with-dependencies.jar:$APPDIR/lib-gremlin-groovy/groovy-1.8.9.jar:$APPDIR/lib-gremlin-groovy/gremlin-java-2.4.0.jar:$APPDIR/lib-gremlin-groovy/gremlin-groovy-2.4.0.jar:$APPDIR/lib-gremlin-groovy/pipes-2.4.0.jar:$APPDIR/lib-gremlin-groovy/antlr-2.7.7.jar:$APPDIR/lib-gremlin-groovy/blueprints-core-2.4.0.jar:$APPDIR/lib/concurrentlinkedhashmap-lru-1.4.jar org.acacia.server.AcaciaServer $STANDALONE_FLAG

X10_NPLACES=$X10_NPLACES x10 -mx8192m -classpath .:$APPDIR/bin/acacia.jar:$APPDIR/log4j.properties:$APPDIR/build:$APPDIR/build/classes:$APPDIR/lib/j2ee-1.4.jar:$APPDIR/lib/inject-api-1.0.0-PRD.jar:$APPDIR/lib/cdi-api-1.0.jar:$APPDIR/lib/commons-httpclient-3.1.jar:$APPDIR/lib/commons-codec-1.4.jar:$APPDIR/lib/commons-daemon-1.0.11.jar:$APPDIR/lib/jackson-core-asl-1.9.7.jar:$APPDIR/lib/jackson-mapper-asl-1.8.8.jar:$APPDIR/lib/jamon-runtime-2.3.1.jar:$APPDIR/lib/libthrift-0.8.0.jar:$APPDIR/lib/geronimo-jta.jar:$APPDIR/lib/metrics-core-2.1.2.jar:$APPDIR/lib/jersey-server-1.9.jar:$APPDIR/lib/jersey-core-1.9.jar:$APPDIR/lib/asm-3.2.jar:$APPDIR/lib/servlet-api-2.5-20081211.jar:$APPDIR/lib/guava-11.0.2.jar:$APPDIR/lib/jetty-6.1.25.jar:$APPDIR/lib/jetty-util-6.1.25.jar:$APPDIR/lib/jettison-1.3.jar:$APPDIR/lib/slf4j-log4j12-1.7.2.jar:$APPDIR/lib/slf4j-api-1.7.2.jar:$APPDIR/lib/log4j-1.2.16.jar:$APPDIR/lib/protobuf-java-2.4.0a.jar:$APPDIR/lib/kryo-3.0.2-SNAPSHOT.jar:$APPDIR/lib/minlog-1.3.1-SNAPSHOT.jar:$APPDIR/lib/objenesis-2.2-SNAPSHOT.jar:$APPDIR/lib/commons-collections-3.2.1.jar:$APPDIR/lib/zookeeper-3.4.5.jar:$APPDIR/lib/hsqldb-2.2.9.jar:$APPDIR/lib/hadoop-core-1.0.4.jar:$APPDIR/lib/hbase-0.94.4.jar:$APPDIR/lib/commons-configuration-1.6.jar:$APPDIR/lib/commons-cli-1.2.jar:$APPDIR/lib/commons-io-1.4.jar:$APPDIR/lib/commons-lang-2.5.jar:$APPDIR/lib/commons-logging-1.1.1.jar:$APPDIR/neo4j-lib/geronimo-jta_1.1_spec-1.1.1.jar:$APPDIR/neo4j-lib/lucene-core-3.5.0.jar:$APPDIR/neo4j-lib/neo4j-cypher-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-graph-algo-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-graph-matching-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-jmx-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-kernel-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-lucene-index-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-shell-1.8.1.jar:$APPDIR/neo4j-lib/neo4j-udc-1.8.1.jar:$APPDIR/neo4j-lib/org.apache.servicemix.bundles.jline-0.9.94_1.jar:$APPDIR/neo4j-lib/scala-library-2.9.1-1.jar:$APPDIR/neo4j-lib/server-api-1.8.1.jar::$APPDIR/neo4j-lib/batch-import-jar-with-dependencies.jar:$APPDIR/lib-gremlin-groovy/groovy-1.8.9.jar:$APPDIR/lib-gremlin-groovy/gremlin-java-2.4.0.jar:$APPDIR/lib-gremlin-groovy/gremlin-groovy-2.4.0.jar:$APPDIR/lib-gremlin-groovy/pipes-2.4.0.jar:$APPDIR/lib-gremlin-groovy/antlr-2.7.7.jar:$APPDIR/lib-gremlin-groovy/blueprints-core-2.4.0.jar:$APPDIR/lib/concurrentlinkedhashmap-lru-1.4.jar:$APPDIR/lib/kryo-shaded-3.0.2-SNAPSHOT.jar org.acacia.server.AcaciaServer $STANDALONE_FLAG
