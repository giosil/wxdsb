#!/bin/bash

kafka-console-consumer.sh --consumer.config /tmp/client.properties --bootstrap-server kafka-dew.default.svc.cluster.local:9092 --topic test --from-beginning

