# Используем базовый образ с JDK
FROM openjdk:20-jdk-slim AS build

WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle/ gradle/

COPY src/ src/

RUN ./gradlew dependencies --no-daemon

# выполнение сборки без тестов
COPY . .
RUN ./gradlew build -x test --no-daemon

FROM openjdk:20-jdk-slim

COPY --from=build /app/build/libs/mixer-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]