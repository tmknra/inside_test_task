FROM gradle:jdk17-jammy AS build
ARG JAR_FILE=*.jar
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM eclipse-temurin:17-jdk-jammy

EXPOSE 9000

RUN mkdir /app

COPY --from=build /home/gradle/src/build /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/libs/spring-boot-application.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/libs/spring-boot-application.jar"]

