FROM gradle:jdk17-jammy AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon
ADD build/libs/inside_test_task-0.0.1-SNAPSHOT.jar application.jar

EXPOSE 9000
RUN mkdir /app

ENTRYPOINT ["java","-jar","application.jar"]
