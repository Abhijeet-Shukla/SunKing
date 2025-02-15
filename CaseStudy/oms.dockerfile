FROM openjdk:17
WORKDIR /app
COPY target/oms-microservice.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
