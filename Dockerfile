FROM openjdk:11-jre-slim

RUN mkdir /app

WORKDIR /app

ADD ./api/target/catalogues-api-1.0.0.jar /app

EXPOSE 8081

CMD java -jar catalogues-api-1.0.0.jar