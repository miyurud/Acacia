#!/bin/bash
KAFKA_HOME=/home/miyurud/software/kafka_2.11-0.10.0.0
echo "Usage : ./kafka-feed-stream.sh /home/miyurud/Acacia/graphs/powergrid.dl"
$KAFKA_HOME/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test < $1
