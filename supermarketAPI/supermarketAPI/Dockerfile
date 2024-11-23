FROM openjdk:21-jdk-slim
WORKDIR /app
COPY build/libs/supermarketapi-0.0.1-plain.jar app_supermarketapi.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app_supermarketapi.jar"]
