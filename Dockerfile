FROM openjdk:17-jdk-slim

WORKDIR /app

COPY pom.xml /app/

COPY src /app/src

RUN apt-get update && apt-get install -y maven && mvn clean install

EXPOSE 8080

CMD ["java", "-jar", "target/nome-do-arquivo.jar"]
