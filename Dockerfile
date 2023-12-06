# Estágio de compilação
FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk maven -y

WORKDIR /app

COPY . .

RUN mvn clean install

# Estágio final
FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /app/target/petshop-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
