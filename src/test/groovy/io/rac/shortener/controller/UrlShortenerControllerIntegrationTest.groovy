package io.rac.shortener.controller

import io.rac.shortener.AbstractIntegrationTestSpecification
import io.rac.shortener.dto.ErrorResponse
import io.rac.shortener.dto.ShortenUrlRequest
import io.rac.shortener.dto.ShortenUrlResponse
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException

class UrlShortenerControllerIntegrationTest extends AbstractIntegrationTestSpecification {

    def "create short url: happy path -> created"() {
        given:
        def originalUrl = "https://www.google.com"
        def request = new ShortenUrlRequest(originalUrl: originalUrl)

        when:
        def response = restTemplate.postForEntity(
                getBaseUrl() + "/api/v1/urls",
                request,
                ShortenUrlResponse.class
        )

        then:
        response.statusCode == HttpStatus.CREATED
        response.body != null
        response.body.shortCode != null
        response.body.originalUrl == originalUrl
        response.headers.getLocation() != null
    }

    def "create short url: invalid url -> bad request"() {
        given:
        def invalidUrl = "invalid-url"
        def request = new ShortenUrlRequest(originalUrl: invalidUrl)

        when:
        restTemplate.postForEntity(
                getBaseUrl() + "/api/v1/urls",
                request,
                ShortenUrlResponse.class
        )

        then:
        def response = thrown(HttpClientErrorException.BadRequest)
        response.statusCode == HttpStatus.BAD_REQUEST

        and:
        def errorBody = response.getResponseBodyAs(ErrorResponse.class)
        errorBody.status == 400
        errorBody.error == "Bad Request"
        errorBody.message.contains("Invalid URL format")
    }

    def "get original url: happy path -> success"() {
        given:
        def originalUrl = "https://www.google.com"
        def request = new ShortenUrlRequest(originalUrl: originalUrl)
        def createResponse = restTemplate.postForEntity(
                getBaseUrl() + "/api/v1/urls",
                request,
                ShortenUrlResponse.class
        )
        def shortCode = createResponse.body.shortCode

        when:
        def response = restTemplate.getForEntity(
                getBaseUrl() + "/api/v1/urls/" + shortCode,
                ShortenUrlResponse.class
        )

        then:
        response.statusCode == HttpStatus.OK
        response.body.originalUrl == originalUrl
        response.body.shortCode == shortCode
    }

    def "get original url: unknown code -> not found"() {
        when:
        restTemplate.getForEntity(getBaseUrl() + "/api/v1/urls/unknownCode", ErrorResponse.class)

        then:
        def response = thrown(HttpClientErrorException.NotFound)
        response.statusCode == HttpStatus.NOT_FOUND

        and:
        def errorBody = response.getResponseBodyAs(ErrorResponse.class)
        errorBody.status == 404
        errorBody.error == "Not Found"
        errorBody.message == "URL not found for code: unknownCode"
    }
}
