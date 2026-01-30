# Short URL Service

A simple URL shortener service that allows users to shorten long URLs and retrieve the original URLs using the shortened version.

## TODO List

- [x] Use Redis for local cache
- [x] Use Postgres as database
- [x] Use Sentry for monitoring
- [x] Use Swagger for API documentation
- [x] Implement security filters (Brute-force protection, etc.)
- [x] Implement Rate Limiting using Redis (Cluster environment support)
- [x] Use ShedLock for scheduled jobs (Cleanup old URLs)
- [x] Create Docker Compose to start DB and Redis
- [x] Implement Integration Tests using Testcontainers
- [x] Implement Unit Tests
- [x] Use Spock + Groovy for testing
- [x] Enable Virtual Threads (Project Loom)
- [x] Add Flyway for database migrations

## Future Improvements
- [ ] Create Docker Compose to build the app and start the service
- [ ] Resilience4j and Circuit Breaker
- [ ] Spring WebFlux to increase performance
- [ ] Add performance tests
- [ ] Monitoring with Grafana
- [ ] Add gitflow instructions and/or jenkins

## How to use

### Prerequisites

- Docker
- Docker Compose
- Java 21 (Required for Virtual Threads/Project Loom)

### Running the application

1.  **Start dependencies (DB and Redis):**
    ```bash
    cd environment
    docker-compose up -d
    ```

2.  **Build and run the service:**
    ```bash
    ./mvnw spring-boot:run
    ```

### API Documentation

Once the application is running, you can access the Swagger UI at:
`http://localhost:8080/swagger-ui.html` (Default URL, may vary based on configuration)
