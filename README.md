# WXDSb - IHE-XDSb implementation

A programmable server and client IHE-XDSb.

## Run locally on Docker

- `git clone https://github.com/giosil/wxdsb.git` 
- `mvn clean install` - this will produce `wxdsb.war` in `target` directory
- `docker build -t <image_name> .` - this will create a Docker image
- `docker run --rm -it -p 8080:8080 <image_name>`

## Contributors

* [Giorgio Silvestris](https://github.com/giosil)
