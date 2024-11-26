FROM openjdk:21-jdk-slim
WORKDIR /app
COPY /build/libs/supermarketapi.jar app_supermarketapi.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_supermarketapi.jar"]
