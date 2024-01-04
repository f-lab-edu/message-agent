FROM openjdk:17
EXPOSE 8080
COPY api/build/libs/api.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","/app.jar"]
