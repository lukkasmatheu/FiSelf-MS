FROM maven:3.9-amazoncorretto-21 as build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21
COPY --from=build /target/*.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]
