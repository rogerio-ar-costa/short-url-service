package io.rac.shortener.controller

import io.rac.shortener.AbstractIntegrationTestSpecification
import io.rac.shortener.dto.ShortenUrlRequest
import io.rac.shortener.dto.ShortenUrlResponse
import org.springframework.http.HttpStatus
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

class RedirectControllerIntegrationTest extends AbstractIntegrationTestSpecification {

    def "redirect: happy path -> success"() {
        given:
        def originalUrl = "https://www.google.com"
        def request = new ShortenUrlRequest(originalUrl: originalUrl)
        def createResponse = restTemplate.postForEntity(getBaseUrl() + "/api/v1/urls", request, ShortenUrlResponse.class)
        def shortCode = createResponse.body.shortCode

        // Configure RestTemplate to NOT follow redirects so we can verify the 302
        def requestFactory = new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                super.prepareConnection(connection, httpMethod)
                connection.setInstanceFollowRedirects(false)
            }
        }
        def noRedirectRestTemplate = new RestTemplate(requestFactory)

        when:
        def response = noRedirectRestTemplate.getForEntity(getBaseUrl() + "/redirect-me/" + shortCode, Void.class)

        then:
        response.statusCode == HttpStatus.FOUND
        response.headers.getLocation().toString() == originalUrl
    }
}
