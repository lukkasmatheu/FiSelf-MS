FROM openjdk:21
workdir /app
COPY target/*.jar demo.jar
EXPOSE 8080
CMD ["java", "-jar", "demo.jar"]


