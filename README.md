# WXDSb - IHE-XDSb implementation

A programmable server and client IHE-XDSb.

## Build

- `git clone https://github.com/giosil/wxdsb.git` 
- `mvn clean install` - this will produce `wxdsb.war` in `target` directory

## Create a Docker image

- `docker build -t <image_name> .` this will create a Docker image named <image_name>

## Run locally on Docker

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
- `docker image inspect <image_name>` - To view info of the image
- `docker logs <container_name>` - To view stdout / stderr of the container
- `docker logs --follow <container_name>` - To follow stdout / stderr of the container
- `docker attach <container_name>` - To connect to the container CTRL+P CTRL+Q to exit
- `docker exec -it <container_name> bash` - Other mode to connect to the container CTRL+Z to exit
- `docker cp <container_name>:/usr/local/tomcat/logs "%USERPROFILE%"\Desktop` - To copy log folder from container to host
- `docker images` - To see the list of images
- `docker image ls` - Other mode to see the list of images
- `docker rmi <image_name>` - To remove image
- `docker pull giosil/wxdsb` - To get this image from Docker Hub (https://hub.docker.com/)

## Run with Kubernetes

Suppose the name of the image is *wxdsb*.

- `kubectl apply -f wxdsb-pod.yaml` - Create pod by manifest
- `kubectl get pods` - To view pods
- `kubectl get events` - To view events in case of debug
- `kubectl logs -f wxdsb` - To view and follow the logs of pod
- `kubectl exec -ti wxdsb -- bash` - To get a shell to the running container
- `kubectl port-forward wxdsb 9090:8080` - Expose (locally) web app by port-forward to local port 9090
- `kubectl delete pod wxdsb` - To delete pod

## Run with Kubernetes using deployment

Suppose the name of the image is *wxdsb*.

- `kubectl apply -f wxdsb-deployment.yaml` - Create deployment by manifest
- `kubectl get pods` - To view pods
- `kubectl get events` - To view events in case of debug
- `kubectl get deployments -l app=wxdsb` - To view deployments by label app=wxdsb
- `kubectl describe deployment/wxdsb` - To view details of deployment
- `kubectl logs -f deployment/wxdsb` - To view and follow the logs of web app
- `kubectl logs -f -l app=wxdsb` - To view and follow the logs of deployment by label app
- `kubectl exec -ti deployment/wxdsb -- bash` - To get a shell to the running container
- `kubectl port-forward deployment/wxdsb 9090:8080` - Expose (locally) web app by port-forward to local port 9090
- `kubectl expose deployment/wxdsb --type="NodePort" --port=80 --target-port=8000` - Expose (internally) web app by service on port 80
- `kubectl get services -l app=wxdsb` - To view service and port assigned
- `kubectl describe services/wxdsb` - To describe service
- `kubectl delete service wxdsb` - To delete service 
- `kubectl delete deployment/wxdsb` - To delete deployment

## REST API Kubernetes

- `kubectl proxy --port=8080` - Start API proxy on local port 8080
	- http://localhost:8080/api/v1/namespaces/default/pods
	- http://localhost:8080/api/v1/namespaces/default/events
	- http://localhost:8080/api/v1/namespaces/default/services
	- http://localhost:8080/apis/apps/v1/namespaces/default/deployments

### Optimize Virtual hard disks on Windows 10

- Shutdown Docker Desktop
- `Optimize-VHD -Path "C:\Users\Public\Documents\Hyper-V\Virtual hard disks\DockerDesktop.vhdx" -Mode Full`
- Start Docker Desktop

### Trace HTTP traffic

`tcpdump -i eth0 -A port 8080 -s 65535 -w tcpdump.log &`

## Contributors

* [Giorgio Silvestris](https://github.com/giosil)
