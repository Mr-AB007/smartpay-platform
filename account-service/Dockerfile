# Use a lightweight JRE image for production safety
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Kaniko copies the jar built in the previous 'build' stage
# Adjust 'target/*.jar' if your build names the jar explicitly
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
