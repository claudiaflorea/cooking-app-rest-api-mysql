# Prepare runtime.
FROM openjdk:11
EXPOSE 8080 61616 61613 9900

# Prepare build workspace.
FROM gradle:6.8.3 AS sdk
USER root
RUN chown -R gradle .
USER gradle
# Copy.
COPY build.gradle .
COPY gradle/wrapper/gradle-wrapper.properties .
COPY src ./src

RUN rm -rf build

# Build, Test and publish.
#RUN gradle bootJar || return 0
#COPY . .
RUN gradle clean bootJar 

RUN ls -l build/libs/

# App image.
FROM ubuntu:16.04

RUN apt-get update && \
    apt-get -y install sudo
RUN sudo apt-get -y install wget 
RUN sudo apt-get -y install unzip
RUN wget -O /var/glowroot-0.13.6-dist.zip https://github.com/glowroot/glowroot/releases/download/v0.13.6/glowroot-0.13.6-dist.zip
RUN unzip '/var/glowroot-0.13.6-dist.zip' && rm  /var/glowroot-0.13.6-dist.zip || true;
RUN ls -l /glowroot
RUN chmod a+x glowroot/glowroot.jar
RUN ls -l /glowroot

FROM openjdk:11 
COPY /build/libs/cooking-0.0.1-SNAPSHOT.jar ./cooking.jar
#ADD /glowroot/glowroot.jar ./glowroot.jar

CMD ["java", "-jar", "cooking.jar"]


