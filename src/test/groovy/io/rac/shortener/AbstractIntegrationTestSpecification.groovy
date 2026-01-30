package io.rac.shortener

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.web.client.RestTemplate
import org.testcontainers.containers.GenericContainer
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractIntegrationTestSpecification extends Specification {

    @Shared
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine")

    @Shared
    @ServiceConnection(name = "redis")
    static GenericContainer redis = new GenericContainer("redis:7-alpine")
            .withExposedPorts(6379)

    @LocalServerPort
    int port

    RestTemplate restTemplate = new RestTemplate()

    String getBaseUrl() {
        "http://localhost:" + port
    }

}
