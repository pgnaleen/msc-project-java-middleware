FROM gradle:7.2.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test
RUN ls /home/gradle/src/build/libs
FROM openjdk:11

WORKDIR /opt/app

COPY --from=build /home/gradle/src/build/libs/sis-admin-service-0.0.1-SNAPSHOT.jar sis-admin-service-0.0.1-SNAPSHOT.jar
COPY ./https.jks https.jks
COPY ./src/main/resources/application.yml application.yml

EXPOSE 8089

ENTRYPOINT ["java", "-Djavax.net.ssl.trustStore=/opt/app/https.jks", "-Djavax.net.ssl.trustStorePassword=test123", "-jar","sis-admin-service-0.0.1-SNAPSHOT.jar"]
