FROM openjdk:11 
COPY /build/libs/cooking-0.0.1-SNAPSHOT.jar ./cooking.jar
COPY /docker/glowroot ./

RUN apt-get update && \ 
    apt install telnet && \
    apt install net-tools

CMD ["java", "-javaagent:glowroot.jar", "-jar", "cooking.jar"]


