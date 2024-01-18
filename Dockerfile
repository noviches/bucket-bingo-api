FROM amazoncorretto:21

LABEL authors="ineg"

ENV ACTIVE_PROFILE="default"

EXPOSE 33728

COPY ./build/libs/api-0.0.1.jar /bbingo/bbingo-0.0.1.jar
WORKDIR /bbingo

ENTRYPOINT ["java", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-jar", "bbingo-0.0.1.jar"]