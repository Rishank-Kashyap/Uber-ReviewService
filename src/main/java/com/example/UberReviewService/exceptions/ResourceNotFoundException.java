package com.example.UberReviewService.exceptions;

// Thrown when a referenced resource (e.g. the Booking a review points to)
// doesn't exist. Mapped to HTTP 404 by GlobalExceptionHandler.
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
