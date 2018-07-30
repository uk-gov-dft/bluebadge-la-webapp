FROM openjdk:8-jre-stretch
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.2.1/wait /wait
RUN chmod +x /wait
CMD ["java","-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n -Djava.compiler=NONE -Dspring.profiles.active=docker", "-jar", "/app.jar"]
