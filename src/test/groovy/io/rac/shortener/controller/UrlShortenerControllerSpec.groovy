//package io.rac.shortener.controller
//
//import io.rac.shortener.IntegrationTestSpecification
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.web.client.TestRestTemplate
//import org.springframework.http.HttpStatus
//
//class UrlShortenerControllerSpec extends IntegrationTestSpecification {
//
//    @Autowired
//    TestRestTemplate restTemplate
//
//    def "should create a short URL via API"() {
//        given:
//        def originalUrl = "https://www.github.com"
//
//        when:
//        def response = restTemplate.postForEntity("/api/v1/urls", originalUrl, String.class)
//
//        then:
//        response.statusCode == HttpStatus.CREATED
//        response.body != null
//        response.headers.getLocation() != null
//    }
//
//    def "should retrieve original URL via API"() {
//        given:
//        def originalUrl = "https://www.linkedin.com"
//        def createResponse = restTemplate.postForEntity("/api/v1/urls", originalUrl, String.class)
//        def shortCode = createResponse.body
//
//        when:
//        def response = restTemplate.getForEntity("/api/v1/urls/" + shortCode, String.class)
//
//        then:
//        response.statusCode == HttpStatus.OK
//        response.body == originalUrl
//    }
//
//    def "should return 404 for unknown short code"() {
//        when:
//        def response = restTemplate.getForEntity("/api/v1/urls/unknownCode", String.class)
//
//        then:
//        response.statusCode == HttpStatus.NOT_FOUND
//    }
//}
