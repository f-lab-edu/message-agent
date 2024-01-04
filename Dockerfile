FROM openjdk:17
EXPOSE 8080
WORKDIR /app
COPY api/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","/app.jar"]
