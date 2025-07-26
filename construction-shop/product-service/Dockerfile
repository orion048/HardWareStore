# Используем легкий образ Java 17
FROM openjdk:17-jdk-slim

# Указываем переменную с путём к WAR-файлу
ARG WAR_FILE=target/*.war

# Копируем WAR-файл внутрь контейнера
COPY ${WAR_FILE} app.war

# Открываем порт 8080
EXPOSE 8080

# Запуск приложения
ENTRYPOINT ["java", "-jar", "/app.war"]"]