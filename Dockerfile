FROM --platform=linux/x86_64 eclipse-temurin:21-jre-alpine
COPY target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT exec java -jar demo.jar

