FROM tomcat:8.0.21-jre8
LABEL maintainer="Ankit Sharma"
EXPOSE 8080
COPY target/maven-web-application.war /usr/local/tomcat/webapps/
