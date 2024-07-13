FROM openjdk:21
LABEL authors="yegor"
ADD target/practproject-0.0.1-SNAPSHOT.jar springboot-docker-demo.jar
ENTRYPOINT ["java", "-jar", "springboot-docker-demo.jar"]