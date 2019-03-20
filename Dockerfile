FROM java:8-jre-alpine
ARG JAR_NAME
COPY "build/libs/${JAR_NAME}" "/usr/src/app/app.jar"
EXPOSE 8080 8081 8000
RUN echo ${JAR_NAME}
CMD ["java","-jar","/usr/src/app/app.jar"]
