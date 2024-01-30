FROM openjdk:21-jdk

WORKDIR /app

COPY build/libs/yigil-admin-0.0.1-SNAPSHOT.jar app.jar

EXPOSE @YIGIL_ADMIN_PORT@

CMD ["java", "-jar", "app.jar"]
