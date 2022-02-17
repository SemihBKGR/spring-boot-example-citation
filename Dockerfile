FROM maven:3.8.4-openjdk-11-slim AS build
COPY src /tale/src
COPY pom.xml /tale
WORKDIR /tale
RUN mvn -q clean package
FROM openjdk:11-slim
COPY --from=build /tale/target/tale.jar app.jar
ENV SERVER_PORT=9000
EXPOSE 9000
ENTRYPOINT ["java","-jar","app.jar"]
