FROM openjdk:21-jdk

WORKDIR /app

COPY build/libs/place-region-batch-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8001

CMD ["java", "-jar", "app.jar"]
