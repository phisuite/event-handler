FROM mozilla/sbt as builder
ENV OPENJDK_TAG=8u232 SBT_VERSION=1.3.8
WORKDIR /app
COPY . .
RUN sbt assembly

FROM openjdk:8-jre-alpine3.9
COPY --from=builder /app/target/scala-2.13/event-handler.jar .
CMD ["java", "-jar", "event-handler.jar"]
