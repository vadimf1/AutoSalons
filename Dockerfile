FROM eclipse-temurin:23.0.2_7-jre-ubi9-minimal
WORKDIR /app
COPY web/target/web-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]