FROM openjdk:21-ea-1-jdk-slim
WORKDIR /app
ARG JAR_FILE=/build/libs/supermarketAPI-0.0.1.jar
COPY ${JAR_FILE} app_supermarketapi.jar
EXPOSE 8080
ENTRYPOINT ["java", "--enable-preview","-jar", "app_supermarketapi.jar"]
