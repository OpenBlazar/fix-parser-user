FROM openjdk:13-alpine

COPY target/user-app-*.jar /opt/user-app.jar

CMD ["java", "-jar", "-Dspring.profiles.active=default", "/opt/user-app.jar"]