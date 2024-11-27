FROM alpine:21
WORKDIR /app
ARG JAR_FILE=build/libs/supermarketAPI-0.0.1.jar
COPY ${JAR_FILE} app_supermarketapi.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_supermarketapi.jar"]
