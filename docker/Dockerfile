# Prepare runtime.
FROM openjdk:8
EXPOSE 8080 61616 61613 9900 4000

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
RUN gradle clean bootJar 

RUN ls -l build/libs/

FROM openjdk:8 
COPY /build/libs/cooking-0.0.1-SNAPSHOT.jar ./cooking.jar
COPY docker/glowroot/lib/glowroot-embedded-collector.jar  lib/glowroot-embedded-collector.jar
COPY docker/glowroot/glowroot.jar ./glowroot.jar

CMD ["java", "-javaagent:glowroot.jar", "-jar", "cooking.jar"]


