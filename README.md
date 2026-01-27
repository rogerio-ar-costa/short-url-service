# Short URL Service

A simple URL shortener service that allows users to shorten long URLs and retrieve the original URLs using the shortened version.

## TODO List

- [ ] Use Redis for local cache
- [ ] Use Postgres as database
- [ ] Use Sentry for monitoring
- [ ] Use Swagger for API documentation
- [ ] Implement security filters (Brute-force protection, etc.)
- [ ] Implement Rate Limiting using Redis (Cluster environment support)
- [ ] Use ShedLock for scheduled jobs (Cleanup old URLs)
- [ ] Create Docker Compose to start DB and Redis
- [ ] Create Docker Compose to build the app and start the service
- [ ] Implement Integration Tests using Testcontainers
- [ ] Implement Unit Tests
- [ ] Use Spock + Groovy for testing
- [ ] Enable Virtual Threads (Project Loom)

## Future Improvements

- [ ] Resilience4j and Circuit Breaker
- [ ] Spring WebFlux to increase performance
- [ ] Add performance tests
- [ ] Monitoring with Grafana

## How to use

### Prerequisites

- Docker
- Docker Compose
- Java 21+ (Required for Virtual Threads/Project Loom)

### Running the application

1.  **Start dependencies (DB and Redis):**
    ```bash
    docker-compose -f docker-compose.yml up -d postgres redis
    ```

2.  **Build and run the service:**
    ```bash
    docker-compose up --build
    ```

### API Documentation

Once the application is running, you can access the Swagger UI at:
`http://localhost:8080/swagger-ui.html` (Default URL, may vary based on configuration)
