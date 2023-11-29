FROM amazoncorretto:17.0.7-alpine

WORKDIR /app

COPY platform/user/build/libs/user-0.0.1-SNAPSHOT.jar /app/.

EXPOSE 8090

CMD ["java", "-jar", "/app/user-0.0.1-SNAPSHOT.jar"]
