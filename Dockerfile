FROM openjdk:17
EXPOSE 8080
ARG JAR_FILE=./api/build/libs/api.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
