# Subscription Service Application

---
### The service provides an API
#### `SubscriptionAPI`
* Create subscriptions

### How does the application works?

* Application receives the requested via `/v1/subscriptions` url with `{userId}`, `{productId}`, `{subscriptionType}` in `{createSubscriptionRequestDto}` like request body.
* Create subscriptions and send queue.

On the swagger page you can find the relevant api endpoint.
You can reach the openapi page by `http://localhost:8080/swagger-ui/index.html` url.

## Technologies

---
- Java 17
- Spring Boot 3.0
- Open API Documentation
- Spring Data JPA
- PostgreSQL
- Restful API
- Docker
- Docker Compose
- RabbitMq

## Prerequisites

---
- Gradle or Docker
---

## Docker Run
You can easily build and run the application using the `Docker` engine.
`Dockerfile` multistage build setup. This means you don't have to perform the build
and run steps separately.

Please follow the below directions in order to build and run the application with Docker Compose;

```sh
$ cd ms-subscription
$ docker-compose up -d
```

Docker compose creates PostgreSQL database instance with port 5432 and RabbitMq instance with port 15672

---
## Maven Run
To build and run the application with `Maven`, please follow the directions below;

```sh
$ cd ms-subscription
$ ./gradlew clean build

$ ./gradlew bootRun

```
You can reach the swagger-ui via  `http://localhost:8080/swagger-ui/index.html`

---
