FROM openjdk:8-jdk-alpine
ENV APP_HOME=/usr/app/
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} employee-services-1.0-SNAPSHOT.jar
EXPOSE 9080
CMD ["java","-jar","employee-services-1.0-SNAPSHOT.jar"]