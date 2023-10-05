# WXDSb - IHE-XDSb implementation

## Run with Kubernetes using deployment

Suppose the name of the image is *wxdsb*. In `k8s` folder do the following:

- `kubectl apply -f wxdsb.yaml` - Create deployment and other kubernetes objects defined in yaml file

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
- `kubectl label pods wxdsb group=test --overwrite` - # Overwrite a Label
- `kubectl label pods wxdsb group-` - # Remove a label
- `kubectl describe service wxdsb-service` - To describe service
- `kubectl delete -f wxdsb.yaml` - Delete all kubernetes objects defined in yaml file
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
