# TODO List

## Completed Tasks

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

## Known Issues
- [ ] Swagger UI might need configuration adjustments if behind a proxy or specific context path.
