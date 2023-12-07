# Etapa de construcción
FROM maven:3.8.6-openjdk-18 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM openjdk:17-jdk-slim
COPY --from=build /app/target/ejercicio-1.0-SNAPSHOT.jar /app/ejercicio.jar
COPY --from=build /app/src/main/resources /app/resources
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "ejercicio.jar"]
