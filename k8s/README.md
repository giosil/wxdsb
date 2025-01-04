# WXDSb - IHE-XDSb implementation

## Run with Kubernetes using POD

Suppose the name of the image is *wxdsb*. In `k8s` folder do the following:

- `kubectl apply -f wxdsb-pod.yaml` - Create pod by manifest

or 

- `kubectl run wxdsb --image=wxdsb:latest` - Run pod from an image

To manage pod:

- `kubectl get pods` - To view pods
- `kubectl get events` - To view events in case of debug
- `kubectl logs -f wxdsb` - To view and follow the logs of pod
- `kubectl exec -ti wxdsb -- bash` - To get a shell to the running container
- `kubectl port-forward wxdsb 9090:8080` - Expose (locally) web app by port-forward to local port 9090
- `kubectl delete pod wxdsb` - To delete pod

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
