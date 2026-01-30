package io.rac.shortener.controller

import io.rac.shortener.AbstractIntegrationTestSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

class UrlShortenerControllerIntegrationTest extends AbstractIntegrationTestSpecification {

    @Autowired
    TestRestTemplate restTemplate

    def "create short url: happy path -> created"() {
        given:
        def originalUrl = "https://www.google.com"

        when:
        def response = restTemplate.postForEntity("/api/v1/urls", originalUrl, String.class)

        then:
        response.statusCode == HttpStatus.CREATED
        response.body != null
        response.headers.getLocation() != null
    }

    def "get original url: happy path -> success"() {
        given:
        def originalUrl = "https://www.google.com"
        def createResponse = restTemplate.postForEntity("/api/v1/urls", originalUrl, String.class)
        def shortCode = createResponse.body

        when:
        def response = restTemplate.getForEntity("/api/v1/urls/" + shortCode, String.class)

        then:
        response.statusCode == HttpStatus.OK
        response.body == originalUrl
    }

    def "get original url: unknown code -> not found"() {
        when:
        def response = restTemplate.getForEntity("/api/v1/urls/unknownCode", String.class)

        then:
        response.statusCode == HttpStatus.NOT_FOUND
    }
}
