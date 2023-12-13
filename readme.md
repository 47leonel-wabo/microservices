# Reactive Microservices with Spring Boot 3.2.x

## Description

Spring Boot project constituted of three services, in which two are builded on reactive stack (user-service and product-service) and one builded on traditional web stack (order-service). Look at the figure below.

## Services

- Product service (reactive web, reactive mongodb)
- User service (R2DBC)
  * dev environment with H2
  * prod environment with postgres
- Order service (JPA) blocking - will use some reactive techniques to mitigate the blocking effect

## Technologies

* Spring Boot 3.x
* JPA
* Spring Web
* Spring WebFlux
* H2
* Mongodb
* PostgreSQL
* R2DBC
* Java
* Linux

## Architecture

![architecture](./screenshots/high_level_architecture.png)
