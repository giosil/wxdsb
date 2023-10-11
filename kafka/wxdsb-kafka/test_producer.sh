#!/bin/bash

kafka-console-producer.sh --producer.config /tmp/client.properties --broker-list kafka-dew-controller-0.kafka-dew-controller-headless.default.svc.cluster.local:9092,kafka-dew-controller-1.kafka-dew-controller-headless.default.svc.cluster.local:9092,kafka-dew-controller-2.kafka-dew-controller-headless.default.svc.cluster.local:9092 --topic test

