# pet-clinic-backend
Ongoing project inspired by Spring Pet Clinic. Developed with Spring Boot, Spring Data JPA, Spring Security, PostgreSQL, Docker, Keycloak

Uses 4 Docker containers:
- app: Spring Boot Resource Server
- postgres: Postgres database holding petclinic data
- keycloak: Keycloak server
- keycloak-postgres: Postgres database holding keycloak data

App and Keycloak server coomunicate using SSL

Run with `docker-compose build && docker-compose up -d`

You need to run [Frontend App](https://github.com/m-saz/pet-clinic-frontend) to gain access

Tests with JUnit, Mockito, H2 in-memory db
