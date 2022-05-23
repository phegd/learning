FROM openjdk:8-jdk-alpine
ENV APP_HOME=/usr/app/
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ExchangeRateService-1.0.0-SNAPSHOT.jar
EXPOSE 9000
CMD ["java","-jar","ExchangeRateService-1.0.0-SNAPSHOT.jar"]