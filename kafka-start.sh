#!/bin/bash
KAFKA_HOME=/home/miyurud/software/kafka_2.11-0.10.0.0
echo "Usage : ./feed-stream.sh /home/miyurud/Acacia/graphs/powergrid.dl"

$KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties &

$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties &

$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
