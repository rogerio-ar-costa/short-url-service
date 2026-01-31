package io.rac.shortener.controller;

import io.rac.shortener.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(
        name = "Redirect",
        description = "Endpoint for redirection"
)
@RestController
@RequestMapping("/redirect-me")
@RequiredArgsConstructor
public class RedirectController {

    private final UrlShortenerService service;

    @Operation(
            summary = "Redirect to original URL",
            description = "Redirects the user to the original URL"
    )
    @GetMapping("/{shortCode}")
    public void redirect(@PathVariable String shortCode, HttpServletResponse response) throws IOException {
        String originalUrl = service.getOriginalUrl(shortCode);
        response.sendRedirect(originalUrl);
    }
}
