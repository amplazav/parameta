# Dockerfile para la aplicacion Spring Boot modular

# Etapa 1: Build
FROM maven:3.9.11-eclipse-temurin-25 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:25-jre-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado en la etapa anterior
COPY --from=build /app/target/parameta-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080 para la aplicación
EXPOSE 8080

# Comando de inicio para ejecutar la aplicacion Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
