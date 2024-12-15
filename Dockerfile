# Используем официальный образ с Java 21
FROM openjdk:21-jdk-slim

# Устанавливаем Maven
RUN apt-get update && apt-get install -y maven

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем исходные файлы в контейнер
COPY . .

# Устанавливаем права на выполнение для mvnw
RUN chmod +x mvnw

# Загружаем зависимости и создаем проект
RUN ./mvnw dependency:go-offline

# Сборка приложения
RUN ./mvnw clean package -DskipTests

# Экспонируем порт 8080 для доступа
EXPOSE 8090

# Запускаем приложение
CMD ["./mvnw", "spring-boot:run"]
