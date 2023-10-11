SET KAFKA_CLI_POD=kafka-dew-client

kubectl exec --tty -i "%KAFKA_CLI_POD%" --namespace default -- bash
