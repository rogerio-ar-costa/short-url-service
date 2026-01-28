package io.rac.shortener.service;

import io.rac.shortener.domain.ShortUrl;
import io.rac.shortener.exception.UrlNotFoundException;
import io.rac.shortener.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlShortenerService {

    private final ShortUrlRepository repository;
    private static final int SHORT_CODE_LENGTH = 8;
    private final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public String shortenUrl(String originalUrl) {
        String shortCode = generateShortCode();
        // Ensure uniqueness (simple retry mechanism, could be improved)
        while (repository.findByShortCode(shortCode).isPresent()) {
            shortCode = generateShortCode();
        }

        ShortUrl shortUrl = ShortUrl.builder()
                .originalUrl(originalUrl)
                .shortCode(shortCode)
                .build();
        
        repository.save(shortUrl);
        log.info("Shortened URL: {} -> {}", originalUrl, shortCode);
        return shortCode;
    }

    @Cacheable(value = "urls", key = "#shortCode")
    public String getOriginalUrl(String shortCode) {
        log.debug("Fetching original URL for code: {}", shortCode);
        return repository.findByShortCode(shortCode)
                .map(ShortUrl::getOriginalUrl)
                .orElseThrow(() -> new UrlNotFoundException("URL not found for code: " + shortCode));
    }

    private String generateShortCode() {
        byte[] randomBytes = new byte[6];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
