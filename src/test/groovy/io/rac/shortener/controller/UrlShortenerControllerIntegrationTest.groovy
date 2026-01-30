package io.rac.shortener.controller

import io.rac.shortener.AbstractIntegrationTestSpecification
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

class UrlShortenerControllerIntegrationTest extends AbstractIntegrationTestSpecification {

    def "create short url: happy path -> created"() {
        given:
        def originalUrl = "https://www.google.com"

        when:
        def response = restTemplate.postForEntity(getBaseUrl() + "/api/v1/urls", originalUrl, String.class)

        then:
        response.statusCode == HttpStatus.CREATED
        response.body != null
        response.headers.getLocation() != null
    }

    def "get original url: happy path -> success"() {
        given:
        def originalUrl = "https://www.google.com"
        def createResponse = restTemplate.postForEntity(getBaseUrl() + "/api/v1/urls", originalUrl, String.class)
        def shortCode = createResponse.body

        when:
        def response = restTemplate.getForEntity(getBaseUrl() + "/api/v1/urls/" + shortCode, String.class)

        then:
        response.statusCode == HttpStatus.OK
        response.body == originalUrl
    }

    def "get original url: unknown code -> not found"() {
        when:
        def response
        try {
            response = restTemplate.getForEntity(getBaseUrl() + "/api/v1/urls/unknownCode", String.class)
        } catch (HttpClientErrorException.NotFound e) {
            response = new ResponseEntity<>(e.getStatusCode())
        }

        then:
        response.statusCode == HttpStatus.NOT_FOUND
    }
}
