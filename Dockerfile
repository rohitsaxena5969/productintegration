FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar productintegration.jar
ENTRYPOINT ["java","-jar","/productintegration.jar"]
EXPOSE 8080
