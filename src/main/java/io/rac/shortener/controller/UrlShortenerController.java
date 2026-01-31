package io.rac.shortener.controller;

import io.rac.shortener.dto.ErrorResponse;
import io.rac.shortener.dto.ShortenUrlRequest;
import io.rac.shortener.dto.ShortenUrlResponse;
import io.rac.shortener.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Tag(
        name = "URL Shortener",
        description = "Endpoints for shortening and retrieving URLs"
)
@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService service;

    @PostMapping
    @Operation(summary = "Shorten a URL", description = "Accepts a long URL and returns a shortened code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "URL successfully shortened",
                    content = @Content(schema = @Schema(implementation = ShortenUrlResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid URL format",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@Valid @RequestBody ShortenUrlRequest request) {
        var shortCode = service.shortenUrl(request.getOriginalUrl());
        var uri = URI.create("/r/" + shortCode);
        var response = ShortenUrlResponse.builder()
                .shortCode(shortCode)
                .originalUrl(request.getOriginalUrl())
                .build();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{shortCode}")
    @Operation(summary = "Get original URL", description = "Returns the original URL for a given short code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL found",
                    content = @Content(schema = @Schema(implementation = ShortenUrlResponse.class))),
            @ApiResponse(responseCode = "404", description = "Short code not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ShortenUrlResponse> getOriginalUrl(@PathVariable String shortCode) {
        var originalUrl = service.getOriginalUrl(shortCode);
        var response = ShortenUrlResponse.builder()
                .shortCode(shortCode)
                .originalUrl(originalUrl)
                .build();
        return ResponseEntity.ok(response);
    }
}
