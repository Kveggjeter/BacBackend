FROM openjdk:17-jdk-alpine
RUN addgroup -S app && adduser -S app -G app
USER app
COPY target/*.jar app.jar
COPY src/keystore.p12 /app/keystore.p12
RUN chmod 644 /app/keystore.p12
EXPOSE 443
ENTRYPOINT ["java", "-jar", "/app.jar"]