# WXDSb - IHE-XDSb implementation

## Install Ingress-Nginx to your Docker Desktop Kubernetes

- `kubectl config current-context`
- `kubectl config use-context docker-desktop`
- `kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.6.4/deploy/static/provider/cloud/deploy.yaml`

## Run with Kubernetes

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
- `kubectl apply -f wxdsb-deployment.yaml` - Create deployment by manifest
- `kubectl get pods` - To view pods
- `kubectl get events` - To view events in case of debug
- `kubectl get deployments -l app=wxdsb` - To view deployments by label app=wxdsb
- `kubectl describe deployments/wxdsb` - To view details of deployment
- `kubectl logs -f deployments/wxdsb` - To view and follow the logs of web app
- `kubectl logs -f -l app=wxdsb` - To view and follow the logs of deployment by label app
- `kubectl exec -ti deployments/wxdsb -- bash` - To get a shell to the running container
- `kubectl port-forward deployments/wxdsb 9090:8080` - Expose (locally) web app by port-forward to local port 9090
- `kubectl expose deployments/wxdsb --type="NodePort" --port=8080 --target-port=8080` - Expose (internally) web app by service.
- `kubectl get services -l app=wxdsb` - To view service and port assigned
- `kubectl describe service wxdsb-service` - To describe service
- `kubectl delete ingress wxdsb-ingress` - To delete ingress
- `kubectl delete service wxdsb-service` - To delete service
- `kubectl delete persistentvolumeclaim wxdsb-pvc` - To delete PersistentVolumeClaim
- `kubectl delete configmap wxdsb-env` - To delete configmap
- `kubectl delete secret  wxdsb-sec` - To delete secret
- `kubectl delete deployment wxdsb` - To delete deployment

## REST API Kubernetes

- `kubectl proxy --port=8080` - Start API proxy on local port 8080
	- http://localhost:8080/api/v1/namespaces/default/pods
	- http://localhost:8080/api/v1/namespaces/default/events
	- http://localhost:8080/api/v1/namespaces/default/services
	- http://localhost:8080/apis/apps/v1/namespaces/default/deployments

