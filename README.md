# WXDSb - IHE-XDSb implementation

A programmable server and client IHE-XDSb.

## Build

- `git clone https://github.com/giosil/wxdsb.git` 
- `mvn clean install` - this will produce `wxdsb.war` in `target` directory
- `mvn dependency:copy-dependencies` - this will copy jars in `target/dependency` directory
- `mvn clean install -f pom2.xml` - this install wxdsb as library

## Create a Docker image

- `docker build -t <image_name> .` this will create a Docker image named <image_name>

## Debug network issues

### Trace HTTP traffic

`tcpdump --list-interfaces` (to show interfaces) or `ip link show`
`tcpdump -i eth0 -A port 8080 -s 65535 -w tcpdump.log &` (-i interface, -A Print each packet in ASCII, -s snaplen, -w file in binary pcap format)
`tcpdump -r tcpdump.log -A` (to read pcap file, -A include ASCII content)

### Enabling SSL debugging

`mvn test -DargLine="-Ddew.test.op=findDocuments -Djavax.net.debug=all"`

`mvn test -DargLine="-Ddew.test.op=findDocuments -Djavax.net.debug=ssl,handshake"`

`mvn test -DargLine="-Ddew.test.op=findDocuments -Djavax.net.debug=ssl:handshake:verbose:keymanager:trustmanager -Djava.security.debug=access:stack"`

`mvn test -DargLine="-Ddew.test.op=findDocuments -Djavax.net.debug=ssl:record:plaintext"`

## Contributors

* [Giorgio Silvestris](https://github.com/giosil)
