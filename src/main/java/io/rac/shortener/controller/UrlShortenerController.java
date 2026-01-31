package io.rac.shortener.controller;

import io.rac.shortener.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
@Tag(name = "URL Shortener", description = "Endpoints for shortening and retrieving URLs")
public class UrlShortenerController {

    private final UrlShortenerService service;

    @PostMapping
    @Operation(summary = "Shorten a URL", description = "Accepts a long URL and returns a shortened code")
    public ResponseEntity<String> shortenUrl(@RequestBody String originalUrl) {
        var shortCode = service.shortenUrl(originalUrl);
        var uri = URI.create("/" + shortCode);
        return ResponseEntity.created(uri).body(shortCode);
    }

    @GetMapping("/{shortCode}")
    @Operation(summary = "Get original URL", description = "Returns the original URL for a given short code")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String shortCode) {
        var originalUrl = service.getOriginalUrl(shortCode);
        return ResponseEntity.ok(originalUrl);
    }
}
