# WXDSb - IHE-XDSb implementation

A programmable server and client IHE-XDSb.

## Run locally on Docker

- `git clone https://github.com/giosil/wxdsb.git` 
- `mvn clean install` - this will produce `wxdsb.war` in `target` directory
- `docker build -t <image_name> .` - this will create a Docker image
- `docker run --rm -it -p 8080:8080 <image_name>` - To run Docker image 
- `docker ps` - To see the running container
- `docker stop <container_name>` - To stop the running container
- `docker ps -a` - To see all container
- `docker rm <container_name>` - To remove the Docker container
- `docker images` - To see the available Docker images
- `docker rmi <image_name>` - To remove Docker image
- `docker pull giosil/wxdsb` - To get this Docker image from https://hub.docker.com/


## Contributors

* [Giorgio Silvestris](https://github.com/giosil)
