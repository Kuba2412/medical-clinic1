FROM openjdk:17-jdk-alpine
MAINTAINER Kuba
COPY target/clinic-medical-0.0.1-SNAPSHOT.jar MedicalClinic.jar
ENTRYPOINT ["java","-jar","MedicalClinic.jar"]

