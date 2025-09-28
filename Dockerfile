FROM openjdk:17-jdk-alpine
LABEL authors="91807"
WORKDIR /app

ENTRYPOINT ["top", "-b"]