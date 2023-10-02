FROM tomcat:9.0.73-jdk8-corretto
ENV DEPLOY_DIR /usr/local/tomcat/webapps
COPY target/wxdsb.war $DEPLOY_DIR
