package io.rac.shortener.service

import io.rac.shortener.domain.ShortUrl
import io.rac.shortener.exception.UrlNotFoundException
import io.rac.shortener.repository.ShortUrlRepository
import spock.lang.Specification
import spock.lang.Subject

class UrlShortenerServiceTest extends Specification {

    ShortUrlRepository repository = Mock()

    @Subject
    UrlShortenerService service = new UrlShortenerService(repository)

    def "short url: happy path -> success"() {
        given:
        def originalUrl = "https://www.google.com"
        def capturedUrl = null

        when:
        def shortCode = service.shortenUrl(originalUrl)

        then:
        1 * repository.findByShortCode(_) >> Optional.empty()
        1 * repository.save(_ as ShortUrl) >> {
            ShortUrl url ->
                capturedUrl = url
                return url
        }

        and:
        shortCode
        capturedUrl
        capturedUrl.originalUrl == originalUrl
        capturedUrl.shortCode == shortCode
    }

    def "short url: collision -> retry success"() {
        given:
        def originalUrl = "https://www.google.com"
        def existingShortUrl = new ShortUrl(
                shortCode: "existing",
                originalUrl: "https://other.com"
        )

        when:
        def shortCode = service.shortenUrl(originalUrl)

        then:
        2 * repository.findByShortCode(_) >>> [Optional.of(existingShortUrl), Optional.empty()]
        1 * repository.save(_)
        shortCode != null
    }

    def "get original url: happy path -> success"() {
        given:
        def shortCode = "abc12345"
        def originalUrl = "https://www.google.com"
        def shortUrl = new ShortUrl(
                shortCode: shortCode,
                originalUrl: originalUrl
        )

        when:
        def result = service.getOriginalUrl(shortCode)

        then:
        1 * repository.findByShortCode(shortCode) >> Optional.of(shortUrl)
        result == originalUrl
    }

    def "get original url: not found -> exception"() {
        given:
        def shortCode = "nonexistent"

        when:
        service.getOriginalUrl(shortCode)

        then:
        1 * repository.findByShortCode(shortCode) >> Optional.empty()
        thrown(UrlNotFoundException)
    }
}
