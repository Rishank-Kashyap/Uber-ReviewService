package com.example.UberReviewService.controllers;

import com.example.UberReviewService.dto.CreateReviewRequest;
import com.example.UberReviewService.dto.ReviewResponse;
import com.example.UberReviewService.entities.PassengerReview;
import com.example.UberReviewService.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/passenger-reviews")
public class PassengerReviewController {

    private final ReviewService reviewService;

    public PassengerReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // POST /api/v1/passenger-reviews  { content, rating, bookingId } -> 201 Created.
    // If bookingId doesn't exist the service throws ResourceNotFoundException,
    // which GlobalExceptionHandler turns into a 404 -- so the controller stays thin.
    @PostMapping
    public ResponseEntity<ReviewResponse> createPassengerReview(@RequestBody CreateReviewRequest request) {
        PassengerReview created = reviewService.createPassengerReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ReviewResponse.from(created));
    }
}
