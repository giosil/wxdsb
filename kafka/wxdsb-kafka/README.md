# wxdsb-kafka

Example of kafka consumer and producer.

## Build

- `mvn clean package`

## Deploy on Kubernetes after helm installation

First get client-password from secret.

- `kubectl get secret kafka-dew-user-passwords --namespace default -o jsonpath='{.data.client-passwords}'`

Remember that the password is base64 encoded.

To decode Base64 encoded text in Powershell:

- `$B64 ='SEl5dzI2MHZOVQ=='`
- `$DEC = [System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String($B64))`
- `Write-Output $DEC`

To decode Base64 encoded text in Linux:

- `echo SEl5dzI2MHZOVQ== | base64 -d`

Replace password in file `client.properties` used by `test_producer.sh` and `test_consumer.sh`.

Replace password in files `wkafkaconsumer.sh` and `wkafkaproducer.sh` to run respectively `org.dew.kafka.WKafkaConsumer` and `org.dew.kafka.WKafkaProducer`.

Execute the following commands:

- `01_run_kafka_client.cmd` - Olny the first time
- `02_deploy.cmd` - to copy files in kafka client POD

Open 2 shell windows and execute the following command:

- `03_exec_bash.cmd`

In first POD shell execute:

- `cd tmp`
- `./wkafkaconsumer.sh`

In second POD shell execute:

- `cd tmp`
- `./wkafkaproducer.sh`

## Contributors

* [Giorgio Silvestris](https://github.com/giosil)
