FROM openjdk:21-jdk

WORKDIR /app

COPY log/build/libs/log-0.0.1-SNAPSHOT-plain.jar app.jar

EXPOSE 9090

CMD ["java", "-jar", "app.jar"]