
FROM amazoncorretto:17.0.7-alpine

VOLUME /tmp

WORKDIR /app

COPY ./build/libs/Seulah-0.0.1-SNAPSHOT.jar /app/


EXPOSE 8085

CMD ["java", "-jar", "/app/Seulah-0.0.1-SNAPSHOT.jar"]


