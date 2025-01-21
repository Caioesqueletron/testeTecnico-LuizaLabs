FROM openjdk:17-jdk-slim
EXPOSE 8080
WORKDIR /app
COPY jar/testeTecnico-0.0.1-SNAPSHOT.jar /testeTecnico-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar", "/testeTecnico-0.0.1-SNAPSHOT.jar"]
