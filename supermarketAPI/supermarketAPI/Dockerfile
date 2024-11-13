# Use a base image wiht Java Runtime environment
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

#Copy the Spring Boot Jar file into the container at /app
COPY build/libs/supermarketapi-plain.jar app.jar

#Expose the port that your application runs on
EXPOSE 8082

#Set the entry point to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]