# Short URL Service

A simple yet powerful URL shortener service that allows users to shorten long URLs and retrieve the original URLs using the shortened version.

## Flow / Use Cases

1.  **Shorten URL**: A user sends a long URL (e.g., `https://www.google.com`) to the service. The service generates a unique short code (e.g., `aBc123`) and stores the mapping.
2.  **Redirect**: A user accesses the short URL (e.g., `http://localhost:8080/aBc123`). The service looks up the original URL and performs a 302 redirect to it.

## Tech Stack

This project uses a modern Java stack focusing on performance and developer experience:

*   **Java 25**: Leveraging the latest Java features.
*   **Spring Boot 4.0.2**: The core framework.
*   **Virtual Threads (Project Loom)**: Enabled for high-throughput concurrency.
*   **PostgreSQL**: Primary relational database.
*   **Redis**: Used for caching and rate limiting.
*   **Flyway**: Database migration management.
*   **Sentry**: Application monitoring and error tracking.
*   **ShedLock**: Distributed lock for scheduled tasks (e.g., cleaning up old URLs).
*   **Spock Framework (Groovy)**: For expressive unit and integration testing.
*   **Testcontainers**: For reliable integration tests with real database/redis instances.
*   **Swagger (OpenAPI)**: API documentation.

## How to Setup and Run Locally

### Prerequisites

*   **Java 25**: Ensure you have a JDK 25 installed.
*   **Docker & Docker Compose**: Required for running dependencies (Postgres, Redis).

### Steps

1.  **Start Infrastructure Dependencies**
    Navigate to the `environment` folder and start the containers:
    ```bash
    cd environment
    docker-compose up -d
    ```

2.  **Run the Application**
    Return to the root directory and start the Spring Boot application:
    ```bash
    cd ..
    ./mvnw spring-boot:run
    ```
    The application will start on port `8080`.

## Testing

The project uses Spock for testing. 

Integration tests use Testcontainers to spin up ephemeral Postgres and Redis instances.

To run all tests (Unit and Integration):

```bash
./mvnw test
```

## Curl Examples

Here is how you can test the API manually using `curl`.

### 1. Shorten a URL

Send a POST request with the raw URL string in the body.

```bash
curl -X POST \
  http://localhost:8080/api/v1/urls \
  -H 'Content-Type: text/plain' \
  -d 'https://www.example.com/some/very/long/url/that/needs/shortening'
```

**Response:**
`aX9zB2` (Example short code)

### 2. Access/Redirect

Access the short code to be redirected to the original URL.

```bash
curl -v http://localhost:8080/aX9zB2
```

**Response Headers:**
```
< HTTP/1.1 302 Found
< Location: https://www.example.com/some/very/long/url/that/needs/shortening
...
```
