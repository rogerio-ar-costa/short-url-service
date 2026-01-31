package io.rac.shortener.service;

import io.rac.shortener.domain.ShortUrl;
import io.rac.shortener.exception.UrlNotFoundException;
import io.rac.shortener.repository.ShortUrlRepository;
import io.rac.shortener.util.ShortCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    private final ShortUrlRepository repository;

    @Cacheable(value = "urls", key = "#shortCode")
    public String getOriginalUrl(String shortCode) {

        log.debug("Fetching original URL for code: {}", shortCode);

        var shortUrl = repository.findByShortCode(shortCode);
        return shortUrl
                .map(ShortUrl::getOriginalUrl)
                .orElseThrow(() ->
                        new UrlNotFoundException("URL not found for code: " + shortCode)
                );
    }

    @Transactional
    public String shortenUrl(String originalUrl) {

        log.debug("Shortening URL: {}", originalUrl);

        String shortCode = calculateUniqueShortCode(originalUrl);
        return saveAndReturn(originalUrl, shortCode);
    }


    private String calculateUniqueShortCode(String originalUrl) {
        String shortCode = ShortCodeGenerator.generateShortCode();
        while (repository.findByShortCode(shortCode).isPresent()) {
            shortCode = ShortCodeGenerator.generateShortCode();
        }
        return shortCode;
    }

    private String saveAndReturn(String originalUrl, String shortCode) {
        ShortUrl shortUrl = ShortUrl.builder()
                .originalUrl(originalUrl)
                .shortCode(shortCode)
                .build();
        repository.save(shortUrl);
        log.info("Shortened URL: {} -> {}", originalUrl, shortCode);
        return shortCode;
    }
}
