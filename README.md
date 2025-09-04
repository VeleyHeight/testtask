# Микросервисная система управления постами
## Описание
Система состоит из четырех микросервисов, взаимодействующих между собой через REST API и Kafka. Все сервисы используют единую точку входа через API Gateway и зарегистрированы в Eureka Server.

## Архитектура
* API Gateway - единая точка входа для всех запросов

* Eureka Server - сервис обнаружения и регистрации микросервисов

* Config Server - централизованное управление конфигурациями

* Users Service - управление пользователями и аутентификация

* Banned Words Service - управление списком запрещенных слов

* Post Service - обработка и публикация постов

* Analytic Post Service - сбор и анализ статистики по постам

## Технологии
* Java 17

* Spring Boot

* Spring Cloud (Gateway, Eureka, Config Server)

* PostgreSQL

* Kafka

* Docker

* MapStruct

* JWT

* Maven

## Запуск системы
Убедитесь, что установлены Docker и Docker Compose

Клонируйте репозиторий

Выполните команду в корневой директории:

~~~ bash
docker-compose up --build
~~~
Система будет развернута и доступна по адресу: http://localhost:8080

## API Endpoints
### Сервис пользователей (/api/users)
* POST /api/users/signup - регистрация пользователя

* POST /api/users/login - авторизация пользователя

* POST /api/users/refresh - обновление JWT токена

### Сервис запрещенных слов (/api/banned-words)
* GET /api/banned-words - получить все запрещенные слова

* POST /api/banned-words - добавить запрещенное слово

* DELETE /api/banned-words/{word} - удалить запрещенное слово

* POST /api/banned-words/check - проверить текст на запрещенные слова

### Сервис постов (/api/posts)
* POST /api/posts/processing - обработать и опубликовать пост

* GET /api/posts/publishing - получить все посты пользователя

### Сервис аналитики (/api/analytic/post)
* GET /api/analytic/post/{id} - получить количество постов пользователя

* GET /api/analytic/post - получить общее количество постов

* GET /api/analytic/post/banned/{id} - получить количество отклоненных постов пользователя

* GET /api/analytic/post/banned - получить общее количество отклоненных постов

* GET /api/analytic/post/approved/{id} - получить количество опубликованных постов пользователя

* GET /api/analytic/post/approved - получить общее количество опубликованных постов

* GET /api/analytic/post/reason/{id} - получить наиболее частую причину отклонения для пользователя

* GET /api/analytic/post/reason - получить наиболее частую причину отклонения для всех

* GET /api/analytic/post/most-banned/{id} - получить наиболее частые запрещенные слова пользователя

* GET /api/analytic/post/most-banned - получить наиболее частые запрещенные слова для всех

### Базы данных
Система использует четыре отдельные базы данных PostgreSQL:

* users-service - для сервиса пользователей

* banned-words-service - для сервиса запрещенных слов

* post-service - для сервиса постов

* analytic-post-service - для сервиса аналитики

### Обмен сообщениями
Для асинхронной коммуникации между сервисами используется Kafka:

* Топик: post-event

* Формат сообщений: AnalyticDTO

### Конфигурация
Все конфигурации хранятся в Config Server и управляются централизованно. Настройки включают:

* Параметры подключения к БД

* JWT секреты и время жизни токенов

* Максимальная длина поста

* Настройки Kafka

### Аутентификация
Используется JWT-аутентификация. Для доступа к защищенным endpoint'ам необходимо:

* Зарегистрироваться или авторизоваться через Users Service

* Получить JWT токен

* Добавить токен в заголовок запроса: Authorization: Bearer <token>

### Мониторинг
* Eureka Dashboard: http://localhost:8761

* Состояние сервисов можно проверить через healthcheck endpoints

Разработка
Для самостоятельной сборки и запуска:

~~~ bash
mvn clean package
docker-compose up --build
~~~
Каждый сервис может быть запущен отдельно через IDE или командную строку с предварительным запуском зависимостей (Kafka, PostgreSQL, Config Server, Eureka).


