FROM openjdk:11.0.7-slim 
LABEL maintainer="eemmiii96@gmail.com" 
COPY target/usuario-0.0.1-SNAPSHOT.jar /opt/usuario-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java","-jar","/opt/usuario-0.0.1-SNAPSHOT.jar"]