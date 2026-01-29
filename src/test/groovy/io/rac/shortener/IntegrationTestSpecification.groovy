//package io.rac.shortener
//
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.test.context.DynamicPropertyRegistry
//import org.springframework.test.context.DynamicPropertySource
//import org.testcontainers.containers.GenericContainer
//import org.testcontainers.containers.PostgreSQLContainer
//import org.testcontainers.spock.Testcontainers
//import spock.lang.Specification
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Testcontainers
//abstract class IntegrationTestSpecification extends Specification {
//
//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
//            .withDatabaseName("shortener")
//            .withUsername("user")
//            .withPassword("password")
//
//    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
//            .withExposedPorts(6379)
//
//    @DynamicPropertySource
//    static void properties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl)
//        registry.add("spring.datasource.username", postgres::getUsername)
//        registry.add("spring.datasource.password", postgres::getPassword)
//        registry.add("spring.data.redis.host", redis::getHost)
//        registry.add("spring.data.redis.port", { redis.getMappedPort(6379) })
//    }
//}
