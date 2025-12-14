# ============================
# 1. Build Stage (Java 21)
# ============================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -DskipTests package

# ============================
# 2. Runtime Stage (Java 21)
# ============================
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]
