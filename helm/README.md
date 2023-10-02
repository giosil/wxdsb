# WXDSb - IHE-XDSb implementation

## Deploy application with Helm

In `heml` folder 

- `helm install wxdsb ./wxdsb` - this install `wxdsb` application from project folder
- `helm get all wxdsb` - this show all info of `wxdsb` application
- `helm uninstall wxdsb` - this uninstall `wxdsb` application

Other commands:

- `helm create wxdsb` - this will create `wxdsb` project folder
- `helm lint wxdsb` - this will check `wxdsb` project folder
- `helm package wxdsb` - this will create package from `wxdsb` project folder (`wxdsb-0.1.0.tgz`)
- `helm install wxdsb ./wxdsb-0.1.0.tgz` - this install `wxdsb` application from package
- `helm upgrade wxdsb ./wxdsb-0.1.0.tgz` - this upgrade `wxdsb` application from package

## Contributors

* [Giorgio Silvestris](https://github.com/giosil)
