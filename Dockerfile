FROM tomcat
ENV DEPLOY_DIR /usr/local/tomcat/webapps
COPY target/wxdsb.war $DEPLOY_DIR
