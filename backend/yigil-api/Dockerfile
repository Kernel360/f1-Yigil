FROM openjdk:21-jdk

WORKDIR /app

COPY build/libs/yigil-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
