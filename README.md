# Newspaper Subscriber

**Newspaper Subscriber** — распределённая микросервисная система для управления подписками на журналы и газеты.
Проект реализован в рамках курсовой работы по дисциплине *«Архитектура клиент-серверных приложений»* (РТУ МИРЭА).

Система позволяет:

* управлять пользователями,
* вести каталог изданий (журналы, категории, серии),
* оформлять, продлевать и отменять подписки,
* взаимодействовать с сервисами через единый GraphQL API.

---

## Основные возможности

* Каталог журналов и газет
* Управление пользователями
* Полный жизненный цикл подписки
* JWT-аутентификация
* Асинхронное взаимодействие сервисов (RabbitMQ)
* Микросервисная архитектура
* Контейнеризация Docker + Docker Compose
* CI/CD через GitHub Actions

---

## Архитектура системы

Система построена на **микросервисной архитектуре** с использованием **API Gateway** и **событийно-ориентированного подхода**.

### Компоненты:

* **API Gateway** — единая точка входа (GraphQL)
* **User Service** — управление пользователями
* **Catalog (Magazine) Service** — журналы, категории, серии
* **Subscription Service** — управление подписками
* **RabbitMQ** — брокер сообщений
* **PostgreSQL** — реляционная база данных
* **Frontend (React SPA)**

---

## Используемые технологии

### Backend

* Spring Boot 3.x
* PostgreSQL
* RabbitMQ
* GraphQL
* JWT Authentication

### Frontend

* React + TypeScript
* Apollo Client
* Nginx

### DevOps

* Docker
* Docker Compose
* GitHub Actions
* Render

---

## Структура репозитория

```text
/
├── APIGateway/
├── UserService/
├── MagazineService/
├── SubscriptionService/
├── Frontend/
├── docker-compose.yml
├── .github/workflows/
└── README.md
```

---

## Запуск проекта локально

### Требования

* Docker
* Docker Compose

### Шаги запуска

```bash
git clone https://github.com/Michail19/NewspaperSubscriber.git
cd NewspaperSubscriber
docker compose up --build
```

После запуска будут доступны:

* **Frontend**: [http://localhost:3000](http://localhost:3000)
* **API Gateway (GraphQL)**: [http://localhost:8080/graphql](http://localhost:8080/graphql)
* **RabbitMQ UI**: [http://localhost:15672](http://localhost:15672)

  * login: `guest`
  * password: `guest`

---

## Тестирование

* Unit- и интеграционные тесты сервисов
* Testcontainers для PostgreSQL и RabbitMQ
* Проверка GraphQL-агрегаций в API Gateway

Тесты автоматически запускаются в CI при каждом push.

---

## CI/CD

Реализован пайплайн на **GitHub Actions**:

1. **Test** — тестирование всех микросервисов
2. **Build** — сборка Docker-образов
3. **Publish** — публикация образов
4. **Deploy** — развёртывание на Render

---

## Деплой

Проект развёрнут на платформе **Render** с использованием Docker Compose.
Обновление выполняется автоматически при изменении основной ветки репозитория.

---

## Назначение проекта

Проект является **учебным MVP**, демонстрирующим:

* микросервисную архитектуру,
* GraphQL API,
* асинхронное взаимодействие через брокер сообщений,
* контейнеризацию и CI/CD.

Может быть расширен:

* платёжными сервисами,
* системой уведомлений,
* аналитикой подписок,
* горизонтальным масштабированием.

