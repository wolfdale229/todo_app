FROM openjdk:8-alpine

COPY target/uberjar/todo_app.jar /todo_app/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/todo_app/app.jar"]
