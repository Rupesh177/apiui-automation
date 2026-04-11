FROM openjdk:21

WORKDIR /app

COPY target/test-data-service.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]