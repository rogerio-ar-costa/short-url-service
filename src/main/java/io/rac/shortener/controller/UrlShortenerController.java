package io.rac.shortener.controller;

import io.rac.shortener.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
        String shortCode = service.shortenUrl(originalUrl);
        return ResponseEntity.created(URI.create("/" + shortCode)).body(shortCode);
    }

    @GetMapping("/{shortCode}")
    @Operation(summary = "Get original URL", description = "Returns the original URL for a given short code")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String shortCode) {
        return ResponseEntity.ok(service.getOriginalUrl(shortCode));
    }
}
