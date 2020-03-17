# WXDSb - IHE-XDSb implementation

A programmable server and client IHE-XDSb.

## Run locally on Docker

- `git clone https://github.com/giosil/wxdsb.git` 
- `mvn clean install` - this will produce `wxdsb.war` in `target` directory
- `docker build -t <image_name> .` - this will create a Docker image
- `docker run --rm -it -p 8080:8080 --name=<container_name> <image_name>` - To run image creating container named <container_name>
- `docker rename <container_name> <new_container_name>` - To rename the container
- `docker ps` - To see the running container
- `docker ps -a -q -f name=<container_name> -f status=running` - To see all (-a) the ids/names (-q) of running container by <container_name>
- `docker ps -a -q -f ancestor=<image_name> -f status=running` - To see all (-a) the ids/names (-q) of running container by <image_name>
- `docker top <container_name>` - To display the running processes of the container
- `docker stats <container_name>` - To display a live stream of the container resource usage statistics
- `docker stop <container_name>` - To stop the running container
- `docker stop $(docker ps -a -q -f ancestor=<image_name> -f status=running)` - To stop the running container by image name (Linux)
- `docker stop @(docker ps -a -q -f ancestor=<image_name> -f status=running)` - To stop the running container by image name (Windows)
- `docker kill <container_name>` - To send KILL signal to the running container
- `docker ps -a` - To see all containers
- `docker rm <container_name>` - To remove the container
- `docker inspect <container_name>` - To view info of the container
- `docker logs <container_name>` - To view stdout / stderr of the container
- `docker logs --follow <container_name>` - To follow stdout / stderr of the container
- `docker attach <container_name>` - To connect to the container CTRL+P CTRL+Q to exit
- `docker exec -it <container_name> bash` - Other mode to connect to the container CTRL+Z to exit
- `docker cp <container_name>:/usr/local/tomcat/logs "%USERPROFILE%"\Desktop` - To copy log folder from container to host
- `docker images` - To see the list of images
- `docker image ls` - Other mode to see the list of images
- `docker rmi <image_name>` - To remove image
- `docker pull giosil/wxdsb` - To get this image from Docker Hub (https://hub.docker.com/)

### Optimize Virtual hard disks on Windows 10

- Shutdown Docker Desktop
- `Optimize-VHD -Path "C:\Users\Public\Documents\Hyper-V\Virtual hard disks\DockerDesktop.vhdx" -Mode Full`
- Start Docker Desktop

## Contributors

* [Giorgio Silvestris](https://github.com/giosil)
