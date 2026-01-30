package io.rac.shortener.controller

import io.rac.shortener.AbstractIntegrationTestSpecification
import org.springframework.http.HttpStatus
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

class RedirectControllerIntegrationTest extends AbstractIntegrationTestSpecification {

    def "redirect: happy path -> success"() {
        given:
        def originalUrl = "https://www.google.com"
        def createResponse = restTemplate.postForEntity(getBaseUrl() + "/api/v1/urls", originalUrl, String.class)
        def shortCode = createResponse.body

        // Configure RestTemplate to NOT follow redirects so we can verify the 302
        // We use a custom interceptor or a request factory.
        // SimpleClientHttpRequestFactory does not have setOutputStreaming(boolean) in some versions,
        // but it SHOULD have it. Let's try without it or use another way.
        // Actually, let's just use a custom RequestFactory that overrides prepareConnection
        def requestFactory = new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                super.prepareConnection(connection, httpMethod)
                connection.setInstanceFollowRedirects(false)
            }
        }
        def noRedirectRestTemplate = new RestTemplate(requestFactory)

        when:
        def response = noRedirectRestTemplate.getForEntity(getBaseUrl() + "/" + shortCode, String.class)

        then:
        response.statusCode == HttpStatus.FOUND
        response.headers.getLocation().toString() == originalUrl
    }
}
