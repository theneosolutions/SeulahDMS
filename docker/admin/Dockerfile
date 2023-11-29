FROM amazoncorretto:17.0.7-alpine

WORKDIR /app

COPY platform/admin/build/libs/admin-0.0.1-SNAPSHOT.jar /app/.

EXPOSE 8090

CMD ["java", "-jar", "/app/admin-0.0.1-SNAPSHOT.jar"]