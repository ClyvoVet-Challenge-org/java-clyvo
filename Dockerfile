# Estágio 1: Build da aplicação com Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
 
COPY pom.xml .
RUN mvn dependency:go-offline
 
COPY src ./src
RUN mvn clean package -DskipTests
 
# Estágio 2: Imagem enxuta para execução
FROM eclipse-temurin:21-jre-alpine
 
RUN addgroup -S clyvoappuser && adduser -S -G clyvoappuser clyvoappuser
WORKDIR /app
 
COPY --from=build /app/target/*.jar app.jar
RUN chown clyvoappuser:clyvoappuser app.jar
USER clyvoappuser
 
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]