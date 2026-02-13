FROM maven:3.9-eclipse-temurin-8 AS builder
WORKDIR /app

# Copy Maven descriptor and download dependencies first (better layer caching)
COPY pom.xml .
RUN mvn -q -e -B dependency:go-offline

# Copy source and build the application
COPY src ./src
RUN mvn -q -e -B clean package -DskipTests

FROM eclipse-temurin:8-jre-jammy
WORKDIR /app

COPY --from=builder /app/target/journalApp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]

