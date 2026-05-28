# SmartPay Banking Platform

SmartPay is an enterprise-style banking microservices platform built using Spring Boot, Kafka, Redis, PostgreSQL, and distributed systems design principles.

The project demonstrates:

* Microservices architecture
* Event-driven communication using Kafka
* API Gateway pattern
* Distributed caching with Redis
* Fault tolerance using Resilience4j
* Distributed request tracing using Correlation IDs
* Synchronous and asynchronous inter-service communication

---

# Tech Stack

* Java 21
* Spring Boot 3
* Spring Cloud Gateway
* OpenFeign
* Apache Kafka
* PostgreSQL
* Redis
* Resilience4j
* Docker Compose
* Maven

---

# Microservices

| Service              | Responsibility                            |
| -------------------- | ----------------------------------------- |
| account-service      | Account management and balance operations |
| transaction-service  | Fund transfer and transaction processing  |
| notification-service | Kafka-based transaction notifications     |
| api-gateway          | Centralized routing and rate limiting     |

---

# Architecture

```text id="1ojpp2"
                         Client
                            |
                     API Gateway
                            |
        ------------------------------------------------
        |                     |                       |
   Account Service     Transaction Service     Notification Service
                              |
                    -------------------
                    |                 |
                 Feign             Kafka
                    |                 |
             Account Validation   Event Notifications
```

---

# Implemented Features

## API Gateway

* Centralized API routing
* Redis-based distributed rate limiting

## Kafka Event-Driven Architecture

* Kafka producer and consumer implementation
* Asynchronous event publishing
* Event-driven notification processing

## Redis Integration

* Redis caching using `@Cacheable`
* Cache eviction using `@CacheEvict`
* Distributed rate limiting

## Feign Client Communication

* Synchronous inter-service communication
* Account validation before transactions
* Balance verification and withdrawal handling

## Fault Tolerance using Resilience4j

* Retry mechanism
* Circuit Breaker
* Graceful fallback handling
* Fail-fast behavior

## Global Exception Handling

* Structured API error responses
* Centralized exception management

## Distributed Request Tracing

* Correlation ID generation and propagation
* MDC-based structured logging
* HTTP and Kafka header propagation

## Structured Logging

* Parameterized logging
* Distributed request traceability

---

# Infrastructure

Dockerized local infrastructure includes:

* PostgreSQL
* Kafka
* Zookeeper
* Redis

---

# Project Structure

```text id="n1puc8"
smartpay-platform/
│
├── account-service/
├── transaction-service/
├── notification-service/
├── api-gateway/
├── docker-compose.yml
└── README.md
```

---

# Running the Project

## Start Infrastructure

```bash id="kt0f3p"
docker compose up -d
```

## Run Microservices

Run each Spring Boot service individually from IntelliJ IDEA:

* account-service
* transaction-service
* notification-service
* api-gateway

---

# Current Workflow

```text id="7u78iw"
Client
  ↓
API Gateway
  ↓
transaction-service
  ↓
Feign call to account-service
  ↓
Balance validation and withdrawal
  ↓
Transaction persistence
  ↓
Kafka event published
  ↓
notification-service consumes event
```

---

# Enterprise Concepts Demonstrated

* Microservices architecture
* API Gateway pattern
* Event-driven architecture
* Distributed caching
* Distributed rate limiting
* Retry pattern
* Circuit Breaker pattern
* Correlation IDs
* Distributed request tracing
* Fault tolerance
* Synchronous vs asynchronous communication

---

# Planned Enhancements

* Zipkin distributed tracing
* Swagger/OpenAPI documentation
* JWT authentication
* DTO validation
* Dockerized service deployment
* React frontend dashboard

---

# Author

Anubhav Ranjan
