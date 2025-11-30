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
RUN ./gradlew clean bootJar --no-daemon

# MAGAZINE-SERVICE
WORKDIR /magazine
COPY MagazineService .
RUN ./gradlew clean bootJar --no-daemon

# SUBSCRIPTION-SERVICE
WORKDIR /subscription
COPY SubscriptionService .
RUN ./gradlew clean bootJar --no-daemon

# USER-SERVICE
WORKDIR /user
COPY UserService .
RUN ./gradlew clean bootJar --no-daemon

# =====================================================================
# 5) ОТКРЫВАЕМ ПОРТЫ
# =====================================================================
EXPOSE 80     # frontend
EXPOSE 8080   # apigateway
EXPOSE 8081   # subscription
EXPOSE 8082   # user
EXPOSE 8083   # magazine
EXPOSE 5432   # postgres
EXPOSE 5672   # rabbitmq

# =====================================================================
# 6) ЗАПУСК ЧЕРЕЗ SUPERVISOR
# =====================================================================
CMD ["supervisord", "-n"]
