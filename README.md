# Like-Minded-backend
___
Backend система для мобильного приложения LIkeMinded:
https://github.com/Dimitrius-dev/Like-Minded-android

## Функционал
___
1. Обеспечения аутентификации пользователей на основе технологии JWT token;
2. Получение данных из базы данных в соответствии с REST интерфейсом;
3. Формирование данных и отправка их в базу данных;
4. Автоматическая первоначальная настройка таблиц баз данных.

Deploy
___
Команда для запуска оркестратора из той директории где находится docker-compose.yml
```
docker compose up --build -d
```
___

Иерархия файлов для запуска docker-compose:
- docker-compose.yml
- likeminded-backend :
  - Dockerfile
  - likeminded-backend.jar

___
Dockerfile  
```
FROM openjdk:20 
ADD ./likeminded-backend.jar likeminded.jar
ENTRYPOINT ["java", "-jar", "likeminded.jar"]
```
___
docker-compose.yml  
[*] - зашифрованные данные
```
version: "3"

services:
  postgres-likeminded:
    container_name: "postgres_likeminded"
    image: postgres
    environment:
      POSTGRES_DB: "like_minded"
      POSTGRES_USER: "admin_bot"
      POSTGRES_PASSWORD: [пароль]
    ports:
      - "5432:5432"

  backend:
    build: ./likeminded-backend/
    depends_on:
      - "postgres-likeminded"
    container_name: "likeminded-backend"
    ports:
      - "9090:9090"
```