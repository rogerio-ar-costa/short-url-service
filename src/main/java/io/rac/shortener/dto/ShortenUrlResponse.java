package io.rac.shortener.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortenUrlResponse {
    
    @Schema(description = "The generated short code", example = "aX9z2P")
    private String shortCode;
    
    @Schema(description = "The original long URL", example = "https://www.google.com/search?q=spring+boot")
    private String originalUrl;
}
