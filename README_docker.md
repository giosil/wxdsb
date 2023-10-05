# Docker Cheat Sheet

## Create a Docker image

- `docker build -t <image_name> .` this will create a Docker image named <image_name>

## Run locally on Docker

- `docker run --rm -it -p 8080:8080 --name=<container_name> -d <image_name>` - To run image creating container named <container_name>
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
- `docker rmi <image_name>` - To remove an image
- `docker image prune` - To remove all unused images
- `docker commit <container_name> <image_name>:<tag>` - To create a new image from a container’s changes
- `docker push <image_name>:<tag>` - To upload an image to Docker Hub (https://hub.docker.com/)
- `docker pull <image_name>:<tag>` - To get an image from Docker Hub (https://hub.docker.com/)
- `docker save <image_name> -o <file_name>.tar` - Save one or more images to a tar archive
- `docker import <file_name>.tar` - Import the contents from a tarball to create a filesystem image
- `docker run -v <path_host>:<path_container> <image_name>` - To run image creating container with a volume
- `docker volume create --name <name_vol> -o type=none -o o=bind -o device=<path_host>` - To create a volume mapped with an host path
- `docker network create <network_name>` - To create a network
- `docker run --name <container_name> --network <network_name> [--alias <alias>] -d <image_name>` - To run image creating container with a network

### Samples

- `docker build -t giosil/wxdsb:1.0.0 .` - To create giosil/wxdsb image
- `docker run -p 8080:8080 --name=wxdsb -d giosil/wxdsb:1.0.0` - To run image *giosil/wxdsb* creating container named *wxdsb*
- `docker logs -f wxdsb` - Fetch and follow the logs of *wxdsb* container
- `docker inspect wxdsb` - To inspect a running *wxdsb* container
- `docker exec -it wxdsb sh` - Open a shell inside a running container *wxdsb* container
- `docker cp ext.tar wxdsb:/usr/local/tomcat/lib` - Copy *ext.tar* in */usr/local/tomcat/lib*  folder of running *wxdsb* container
- `docker exec -w /usr/local/tomcat/lib wxdsb /bin/tar -xvf /usr/local/tomcat/lib/ext.tar` - Extract tar content on running *wxdsb* container
- `docker exec wxdsb /bin/rm -fr /usr/local/tomcat/lib/ext.tar` - Remove tar on running *wxdsb* container
- `docker commit wxdsb giosil/wxdsb:1.1.0` - To create new version of *giosil/wxdsb* image from the *wxdsb* container
- `docker tag giosil/wxdsb:1.1.0 giosil/wxdsb:latest` - To add tag *latest* to *giosil/wxdsb:1.1.0* image
- `docker login dockerhub.dew.org` - To access to another registry the first time before push
- `docker login -u giosil` - To access to Docker Hub the first time before push
- `docker push giosil/wxdsb` - To upload *giosil/wxdsb* to Docker Hub (https://hub.docker.com/)
- `docker pull giosil/wxdsb` - To get *giosil/wxdsb* from Docker Hub (https://hub.docker.com/)

## Docker-compose

Sample of docker-compose.yml:

```yaml
services:
  wxdsb:
    build: .
    ports:
      - "8080:8080"
```

Run container:

`docker compose up -d`

or

`docker-compose up -d`

Stop container:

`docker compose stop`

Stop and remove container (and network):

`docker compose down`

## Run with Kubernetes using POD

Suppose the name of the image is *wxdsb*. In `k8s` folder do the following:

- `kubectl apply -f wxdsb-pod.yaml` - Create pod by manifest
- `kubectl get pods` - To view pods
- `kubectl get events` - To view events in case of debug
- `kubectl logs -f wxdsb` - To view and follow the logs of pod
- `kubectl exec -ti wxdsb -- bash` - To get a shell to the running container
- `kubectl port-forward wxdsb 9090:8080` - Expose (locally) web app by port-forward to local port 9090
- `kubectl delete pod wxdsb` - To delete pod

## Run with Kubernetes using deployment

Suppose the name of the image is *wxdsb*. In `k8s` folder do the following:

- `kubectl apply -f wxdsb.yaml` - Create deployment and other kubernetes objects by manifest

The file `wxdsb.yaml` contains:

- `wxdsb-configmap.yaml` - A ConfigMap is an API object used to store non-confidential data in key-value pairs.
- `wxdsb-secret.yaml` - A Secret is an object that contains a small amount of sensitive data such as a password, a token, or a key.
- `wxdsb-pvc.yaml` - A PersistentVolumeClaim (PVC) is a request for storage by a user.
- `wxdsb-deployment.yaml` - A Deployment provides declarative updates for Pods and ReplicaSets.
- `wxdsb-service.yaml` - A Service is a method for exposing a network application that is running as one or more Pods in your cluster.
- `wxdsb-ingress.yaml` - An Ingress is an API object that manages external access to the services in a cluster, typically HTTP.

Other commands:

- `kubectl config view` - Displays merged kubeconfig settings or a specified kubeconfig file
- `kubectl cluster-info` - Display addresses of the master and services
- `kubectl get nodes` - Provides essential information about the nodes in your Kubernetes cluster 
- `kubectl get nodes -o wide` - Provides more information about the nodes in your Kubernetes cluster
- `kubectl get namespaces --show-labels` - To view namespaces
- `kubectl get all -l app.kubernetes.io/managed-by=Helm'` - To view all objects with label app.kubernetes.io/managed-by=Helm
- `kubectl get pods` - To view pods
- `kubectl get pods -l app.kubernetes.io/name=wxdsb` - To view pods with label app.kubernetes.io/name=wxdsb
- `kubectl get pods -o yaml` - To view pods information in yaml format
- `kubectl get pods -o json` - To view pods information in json format
- `kubectl get pods --all-namespaces` - To view pods of all namespaces
- `kubectl get pv` - List PersistentVolumes
- `kubectl get pvc` - List PersistentVolumeClaims
- `kubectl get events` - To view events in case of debug
- `kubectl events --types=Warning` - To view Warning events
- `kubectl get deployments -l app=wxdsb` - To view deployments by label app=wxdsb
- `kubectl describe deployments/wxdsb` - To view details of deployment
- `kubectl logs -f deployments/wxdsb` - To view and follow the logs of web app
- `kubectl logs -f -l app=wxdsb` - To view and follow the logs of deployment by label app
- `kubectl exec -ti deployments/wxdsb -- bash` - To get a shell to the running first pod of deployment
- `kubectl exec deployments/wxdsb -- env` - To print environment variable from first pod of deployment
- `kubectl port-forward deployments/wxdsb 9090:8080` - Expose (locally) web app by port-forward to local port 9090
- `kubectl expose deployments/wxdsb --type="NodePort" --port=8080 --target-port=8080` - Expose (internally) web app by service.
- `kubectl get services -l app=wxdsb` - To view service and port assigned
- `kubectl run -i --tty busybox --image=busybox:latest -- sh` - Run pod as interactive shell
- `kubectl create job hello --image=busybox:latest -- echo "Hello World"` - Create a Job which prints "Hello World"
- `kubectl create cronjob hello --image=busybox:latest --schedule="*/1 * * * *" -- echo "Hello World"` - Create a CronJob that prints "Hello World" every minute
- `kubectl get jobs --watch` - Watch jobs
- `kubectl label pods wxdsb group=test` - # Add a Label
- `kubectl label pods wxdsb group=test --overwrite` - # Add a Label
- `kubectl label pods wxdsb group-` - # Remove a label
- `kubectl describe service wxdsb-service` - To describe service
- `kubectl delete ingress wxdsb-ingress` - To delete ingress
- `kubectl delete service wxdsb-service` - To delete service
- `kubectl delete persistentvolumeclaim wxdsb-pvc` - To delete PersistentVolumeClaim
- `kubectl delete configmap wxdsb-env` - To delete configmap
- `kubectl delete secret  wxdsb-sec` - To delete secret
- `kubectl delete deployment wxdsb` - To delete deployment
- `kubectl delete namespace dew` - To delete the namespace `dew`
- `kubectl scale --replicas=3 rs/wxdsb` - Scale a replicaset named `wxdsb` to 3
- `kubectl cp test.txt wxdsb:/data01` - Copy local file `test.txt` to remote directory `/data01` of pod named `wxdsb`
- `kubectl cp test.txt wxdsb:/data01` - Copy local file `test.txt` to remote directory `/data01` of pod named `wxdsb`
- `kubectl cp wxdsb:/data01/test.txt .` - Copy remote file `/data01/test.txt` of pod named `wxdsb` in local current (.) directory

## Install Ingress-Nginx to your Docker Desktop Kubernetes

- `kubectl config current-context`
- `kubectl config use-context docker-desktop`
- `kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.6.4/deploy/static/provider/cloud/deploy.yaml`

## REST API Kubernetes

`kubectl proxy --port=8080` - Start API proxy on local port 8080

- Nodes:
	- http://localhost:8080/api/v1/nodes
- Workloads:
	- http://localhost:8080/api/v1/namespaces/default/pods
	- http://localhost:8080/apis/apps/v1/namespaces/default/deployments
	- http://localhost:8080/apis/apps/v1/namespaces/default/replicasets
	- http://localhost:8080/apis/batch/v1/namespaces/default/jobs
	- http://localhost:8080/apis/batch/v1/namespaces/default/cronjobs
- Config:
	- http://localhost:8080/api/v1/namespaces/default/configmaps
	- http://localhost:8080/api/v1/namespaces/default/secrets
- Network:
	- http://localhost:8080/api/v1/namespaces/default/services
	- http://localhost:8080/api/v1/namespaces/default/endpoints
	- http://localhost:8080/apis/networking.k8s.io/v1/namespaces/default/ingresses
	- http://localhost:8080/apis/networking.k8s.io/v1/ingressclasses
- Storage:
	- http://localhost:8080/apis/storage.k8s.io/v1/storageclasses
	- http://localhost:8080/api/v1/namespaces/default/persistentvolumeclaims
	- http://localhost:8080/api/v1/persistentvolumes
- Events:
	- http://localhost:8080/api/v1/namespaces/default/events
- Access Control:
	- http://localhost:8080/api/v1/namespaces/default/serviceaccounts
- Namespaces:
	- http://localhost:8080/api/v1/namespaces

## Deploy application with Helm

In `heml` folder do the following:

- `helm install wxdsb ./wxdsb` - this install `wxdsb` application from project folder
- `helm list` - this show all releases
- `helm get all wxdsb` - this show all info of `wxdsb` application
- `helm uninstall wxdsb` - this uninstall `wxdsb` application

Other commands:

- `helm create wxdsb` - this will create `wxdsb` project folder
- `helm lint wxdsb` - this will check `wxdsb` project folder
- `helm template wxdsb ./wxdsb` - this render chart templates locally and display the output
- `helm show all ./wxdsb` - this will show all information of the chart
- `helm status wxdsb` - this will display the status of the named release
- `helm history sira-drupal` - this will prints historical revisions for a given release
- `helm package wxdsb` - this will create package from `wxdsb` project folder (`wxdsb-0.1.0.tgz`)
- `helm install wxdsb ./wxdsb-0.1.0.tgz` - this install `wxdsb` application from package
- `helm upgrade wxdsb ./wxdsb-0.1.0.tgz` - this upgrade `wxdsb` application from package
- `helm upgrade -i wxdsb ./wxdsb-0.1.0.tgz` - this upgrade `wxdsb` application with `-i` (install if name doesn't exist)

### Optimize Virtual hard disks on Windows 10 (Hyper-V)

- Shutdown Docker Desktop
- `Optimize-VHD -Path "C:\Users\Public\Documents\Hyper-V\Virtual hard disks\DockerDesktop.vhdx" -Mode Full`
- Start Docker Desktop

### Optimize Virtual hard disks on Windows 10 (WSL 2 based engine)

- Shutdown Docker Desktop
- wsl --shutdown
- diskpart
	- select vdisk file="C:\Users\dew\AppData\Local\Docker\wsl\data\ext4.vhdx"
	- attach vdisk readonly
	- compact vdisk
	- detach vdisk
	- exit
- Start Docker Desktop
- \\wsl$ (to view data)
