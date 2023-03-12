# syntax=docker/dockerfile:1
FROM openjdk:21-oraclelinux8

WORKDIR /app/


COPY .mvn/ .mvn
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml ./


RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw","spring-boot:run"]
