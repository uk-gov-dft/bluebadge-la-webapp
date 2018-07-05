FROM openjdk:8-jre-stretch
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
