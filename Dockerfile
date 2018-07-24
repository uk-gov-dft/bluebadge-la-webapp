FROM openjdk:8-jre-stretch
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
CMD ["java","-Dspring.profiles.active=docker", "-jar", "/app.jar"]
