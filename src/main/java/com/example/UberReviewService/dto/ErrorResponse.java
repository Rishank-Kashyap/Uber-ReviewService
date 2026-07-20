package com.example.UberReviewService.dto;

import org.springframework.http.HttpStatus;

import java.time.Instant;

// Response DTO for the error/sad path. Every failure is returned in this same
// shape, so clients can parse errors uniformly instead of reading a bare String
// body. The `of(...)` factory keeps construction in one place (mirrors the
// ReviewResponse.from(...) style used for the success path).
public record ErrorResponse(int status, String error, String message, String path, Instant timestamp) {

    public static ErrorResponse of(HttpStatus status, String message, String path) {
        return new ErrorResponse(
                status.value(),             // numeric code, e.g. 404
                status.getReasonPhrase(),   // human phrase, e.g. "Not Found"
                message,                    // the exception's message
                path,                       // the request URI that failed
                Instant.now()
        );
    }
}
