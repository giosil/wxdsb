# WXDSb - IHE-XDSb implementation

## Deploy application with Helm

In this folder named `heml`:

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
- `helm history wxdsb` - this will prints historical revisions for a given release
- `helm package wxdsb` - this will create package from `wxdsb` project folder (`wxdsb-0.1.0.tgz`)
- `helm install wxdsb ./wxdsb-0.1.0.tgz` - this install `wxdsb` application from package
- `helm upgrade wxdsb ./wxdsb-0.1.0.tgz` - this upgrade `wxdsb` application from package
- `helm upgrade -i wxdsb ./wxdsb-0.1.0.tgz` - this upgrade `wxdsb` application with `-i` (install if name doesn't exist)

## Contributors

* [Giorgio Silvestris](https://github.com/giosil)
