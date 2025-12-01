# =====================================================================
# 1) БАЗОВЫЙ ОБРАЗ — Ubuntu
# =====================================================================
FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y \
    curl wget gnupg lsb-release supervisor unzip nano \
    openjdk-21-jdk \
    postgresql postgresql-contrib \
    rabbitmq-server \
    nodejs npm \
    && rm -rf /var/lib/apt/lists/*

# =====================================================================
# 2) КОНФИГ SUPERVISOR (он запустит все сервисы параллельно)
# =====================================================================
RUN mkdir -p /var/log/supervisor
COPY supervisor.conf /etc/supervisor/conf.d/supervisor.conf

# =====================================================================
# 3) FRONTEND (React + NGINX)
# =====================================================================

# Удаляем старый Node.js 12, который идет в образе Ubuntu
RUN apt-get remove -y nodejs libnode-dev && apt-get autoremove -y

# Устанавливаем Node.js 18 — react-scripts работает корректно
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs

# — Сборка фронтенда
WORKDIR /frontend
COPY Frontend/package*.json ./
RUN npm install
COPY Frontend .
RUN npm run build

# — Установка nginx
RUN apt-get update && apt-get install -y nginx && rm -rf /var/lib/apt/lists/*

COPY Frontend/nginx.conf /etc/nginx/conf.d/default.conf
RUN rm /etc/nginx/sites-enabled/default || true
RUN mkdir -p /usr/share/nginx/html
RUN cp -r /frontend/build/* /usr/share/nginx/html/

# =====================================================================
# 4) JAVA МИКРОСЕРВИСЫ (сборка gradle)
# =====================================================================

# APIGATEWAY
WORKDIR /apigateway
COPY APIGateway .
RUN chmod +x gradlew
RUN ./gradlew clean bootJar --no-daemon

# MAGAZINE-SERVICE
WORKDIR /magazine
COPY MagazineService .
RUN chmod +x gradlew
RUN ./gradlew clean bootJar --no-daemon

# SUBSCRIPTION-SERVICE
WORKDIR /subscription
COPY SubscriptionService .
RUN chmod +x gradlew
RUN ./gradlew clean bootJar --no-daemon

# USER-SERVICE
WORKDIR /user
COPY UserService .
RUN chmod +x gradlew
RUN ./gradlew clean bootJar --no-daemon

# =====================================================================
# 5) ОТКРЫВАЕМ ПОРТЫ
# =====================================================================
# frontend
EXPOSE 80

# apigateway
EXPOSE 8080

# subscription
EXPOSE 8081

# user
EXPOSE 8082

# magazine
EXPOSE 8083

# rabbitmq
EXPOSE 5672

# postgres
EXPOSE 5432

# =====================================================================
# 6) ЗАПУСК ЧЕРЕЗ SUPERVISOR
# =====================================================================
CMD ["supervisord", "-n"]
