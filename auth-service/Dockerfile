FROM amazoncorretto:21
VOLUME /tmp
COPY target/*.jar  app.jar
# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]