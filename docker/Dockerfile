FROM openjdk:11 
COPY /build/libs/cooking-0.0.1-SNAPSHOT.jar ./cooking.jar
COPY /docker/glowroot ./

#FROM gradle:6.9.0-jdk11 AS build
#COPY --chown=gradle:gradle . /home/gradle/src
#WORKDIR /home/gradle/src
#RUN gradle build --no-daemon 

# Dockerize is needed to sync containers startup
ENV DOCKERIZE_VERSION v0.6.0
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz

RUN mkdir ~/jenkins

RUN apt-get update && \ 
    apt install telnet && \
    apt install net-tools

CMD ["java", "-javaagent:glowroot.jar", "-jar", "cooking.jar"]


