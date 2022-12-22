FROM eclipse-temurin:17-jdk-alpine

EXPOSE 8080
RUN mkdir /app
COPY ./build/libs/setlist-to-playlist.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-boot-application.jar"]