FROM maven:3.9-eclipse-temurin-21-alpine as build
WORKDIR /app
COPY . .
RUN mvn clean package -Dmaven.test.failure.ignore=true

FROM amazoncorretto:21.0.4-alpine3.20
EXPOSE 8080
COPY --from=build /app/target/identity-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java", "-jar" , "app.jar" ]