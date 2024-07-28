FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/phonebook-api-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} phonebook-api.jar
ENTRYPOINT ["java","-jar","/phonebook-api.jar"]
