FROM openjdk:21-jdk

WORKDIR /app

COPY build/libs/yigil-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE @YIGIL_API_PORT@

CMD ["java", "-jar", "app.jar"]
