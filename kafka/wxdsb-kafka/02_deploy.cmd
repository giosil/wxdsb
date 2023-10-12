SET KAFKA_CLI_POD=kafka-dew-client

kubectl cp --namespace default client.properties "%KAFKA_CLI_POD%":/tmp/client.properties
kubectl cp --namespace default ./target/wxdsb-kafka-1.0.0.jar "%KAFKA_CLI_POD%":/tmp/wxdsb-kafka-1.0.0.jar

kubectl cp --namespace default test_consumer.sh "%KAFKA_CLI_POD%":/tmp/test_consumer.sh
kubectl exec "%KAFKA_CLI_POD%" --namespace default -- chmod +x /tmp/test_consumer.sh

kubectl cp --namespace default test_producer.sh "%KAFKA_CLI_POD%":/tmp/test_producer.sh
kubectl exec "%KAFKA_CLI_POD%" --namespace default -- chmod +x /tmp/test_producer.sh

kubectl cp --namespace default wkafkaconsumer.sh "%KAFKA_CLI_POD%":/tmp/wkafkaconsumer.sh
kubectl exec "%KAFKA_CLI_POD%" --namespace default -- chmod +x /tmp/wkafkaconsumer.sh

kubectl cp --namespace default wkafkaproducer.sh "%KAFKA_CLI_POD%":/tmp/wkafkaproducer.sh
kubectl exec "%KAFKA_CLI_POD%" --namespace default -- chmod +x /tmp/wkafkaproducer.sh

