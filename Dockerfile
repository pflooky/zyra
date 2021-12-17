FROM openjdk:11-jre-slim-buster

MAINTAINER "Peter Flook<pflooky@gmail.com>"

COPY build/libs/*.jar /opt/app/app.jar

CMD ["java", "-jar", "/opt/app/app.jar"]