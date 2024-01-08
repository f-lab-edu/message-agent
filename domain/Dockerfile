FROM openjdk:17
EXPOSE 8080
ARG JAR_FILE=/build/libs/domain.jar
COPY ${JAR_FILE} domain.jar
ENTRYPOINT ["java","-jar","/domain.jar"]
