FROM openjdk:17-jdk-alpine
RUN addgroup -S app && adduser -S app -G app
COPY target/*.jar app.jar
USER app
COPY src/keystore.p12 /app/keystore.p12
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]