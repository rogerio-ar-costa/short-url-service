//package io.rac.shortener.service
//
//import io.rac.shortener.IntegrationTestSpecification
//import io.rac.shortener.repository.ShortUrlRepository
//import org.springframework.beans.factory.annotation.Autowired
//
//class UrlShortenerServiceSpec extends IntegrationTestSpecification {
//
//    @Autowired
//    UrlShortenerService service
//
//    @Autowired
//    ShortUrlRepository repository
//
//    def "should shorten a URL and retrieve the original one"() {
//        given:
//        def originalUrl = "https://www.google.com"
//
//        when:
//        def shortCode = service.shortenUrl(originalUrl)
//
//        then:
//        shortCode != null
//        shortCode.length() > 0
//
//        and:
//        def retrievedUrl = service.getOriginalUrl(shortCode)
//        retrievedUrl == originalUrl
//    }
//
//    def "should generate different codes for the same URL (if implemented that way) or handle idempotency"() {
//        given:
//        def originalUrl = "https://www.example.com"
//
//        when:
//        def code1 = service.shortenUrl(originalUrl)
//        def code2 = service.shortenUrl(originalUrl)
//
//        then:
//        code1 != null
//        code2 != null
//        // In the current implementation, we generate a new code every time.
//        // If we wanted idempotency (same URL -> same code), we'd check that here.
//        code1 != code2
//    }
//}
