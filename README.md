# SmartPay Banking Platform

A banking microservices platform built using Spring Boot and event-driven architecture principles.

## Tech Stack

- Java 21
- Spring Boot
- Spring Cloud Gateway
- Apache Kafka
- PostgreSQL
- Redis
- Docker Compose
- Maven

---

## Microservices

| Service | Responsibility |
|---|---|
| account-service | Account creation and balance management |
| transaction-service | Fund transfer and transaction processing |
| notification-service | Transaction notifications |
| api-gateway | API routing and rate limiting |

---

## Architecture

```text
                Client
                   |
             API Gateway
                   |
    --------------------------------
    |              |              |
Account Service  Transaction   Notification
                    Service       Service
                       |
                    Kafka
                       |
                 Fraud Detection
```

---

## Infrastructure

Dockerized local infrastructure includes:

- PostgreSQL
- Kafka
- Zookeeper
- Redis

---

## Running the Project

### Start Infrastructure

```bash
docker compose up -d
```

### Run Services

Run each Spring Boot service individually from IntelliJ IDEA.

---

## Current Features

- Dockerized infrastructure
- PostgreSQL integration
- Kafka setup
- Redis setup
- Account service APIs
- Multi-service architecture

---

## Upcoming Features

- Kafka event publishing
- Distributed transaction flow
- Rate limiting
- Circuit breaker
- Retry handling
- Fault tolerance
- Fraud detection
- React frontend

---

## Author

Anubhav Ranjan