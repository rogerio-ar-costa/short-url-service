package io.rac.shortener.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    @Schema(description = "Timestamp of the error", example = "2023-10-27T10:15:30")
    private LocalDateTime timestamp;
    
    @Schema(description = "HTTP status code", example = "404")
    private int status;
    
    @Schema(description = "Error type", example = "Not Found")
    private String error;
    
    @Schema(description = "Detailed error message", example = "URL not found for code: aX9z2P")
    private String message;
    
    @Schema(description = "Request path", example = "/api/v1/urls/aX9z2P")
    private String path;
}
