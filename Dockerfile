FROM openjdk:21
COPY --from=build /target/*.jar demo.jar
EXPOSE 8080
CMD ["java", "-jar", "demo.jar"]


