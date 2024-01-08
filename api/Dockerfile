FROM openjdk:17
EXPOSE 8080
ARG JAR_FILE=./api/build/libs/api.jar
COPY ${JAR_FILE} api.jar
ENTRYPOINT ["java","-jar","/api.jar"]
