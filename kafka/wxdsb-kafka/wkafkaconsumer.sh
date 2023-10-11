#!/bin/bash

LIB_PATH="/opt/bitnami/kafka/libs"

CP="./wxdsb-kafka-1.0.0.jar"
CP="$CP:$LIB_PATH/kafka-clients-3.5.1.jar"
CP="$CP:$LIB_PATH/lz4-java-1.8.0.jar"
CP="$CP:$LIB_PATH/snappy-java-1.1.10.1.jar"
CP="$CP:$LIB_PATH/zstd-jni-1.5.5-1.jar"
CP="$CP:$LIB_PATH/slf4j-api-1.7.36.jar"

/opt/bitnami/java/bin/java -cp $CP org.dew.kafka.WKafkaConsumer kafka-dew.default.svc.cluster.local:9092 user1 HIyw260vNU test group

