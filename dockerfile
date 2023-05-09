FROM openjdk:17 AS build
WORKDIR /app
COPY . .
RUN sed -i -e 's/\r$//' mvnw
RUN chmod +x mvnw
RUN ./mvnw clean package

FROM openjdk:17
WORKDIR /app
EXPOSE 8080
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]