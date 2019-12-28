FROM openjdk:13-alpine

ARG VERSION=*

COPY target/user-app-${VERSION}.jar /opt/user-app.jar

CMD ["java", "-jar", "-Dspring.profiles.active=default", "/opt/user-app.jar"]