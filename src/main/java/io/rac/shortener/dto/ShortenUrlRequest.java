package io.rac.shortener.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenUrlRequest {

    @Schema(
            description = "The original long URL to be shortened",
            example = "https://www.google.com/search?q=spring+boot"
    )
    @NotBlank(message = "Original URL is required")
    @URL(message = "Invalid URL format")
    private String originalUrl;
}
