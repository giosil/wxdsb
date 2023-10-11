SET KAFKA_CLI_POD=kafka-dew-client

kubectl run "%KAFKA_CLI_POD%" --image docker.io/bitnami/kafka:3.5.1-debian-11-r71 --namespace default --command -- sleep infinity

