package com.example.UberReviewService.controllers;

import com.example.UberReviewService.dto.ReviewResponse;
import com.example.UberReviewService.exceptions.ResourceNotFoundException;
import com.example.UberReviewService.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // GET /api/v1/reviews -> all reviews (both types) as ReviewResponse DTOs.
    // We map to a DTO so serialization doesn't follow the Review <-> Booking
    // cycle (which would recurse infinitely with raw entities).
    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        List<ReviewResponse> reviews = reviewService.findAllReviews()
                .stream()
                .map(ReviewResponse::from)
                .toList();
        return ResponseEntity.ok(reviews);
    }

    // GET /api/v1/reviews/{id} -> one review as a ReviewResponse. A missing id
    // throws ResourceNotFoundException (via orElseThrow), which the advice turns
    // into the same structured 404 as POST and DELETE -- one consistent error shape.
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable Long id) {
        return reviewService.findReviewById(id)
                .map(ReviewResponse::from)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
    }

    // DELETE /api/v1/reviews/{id} -> 204 No Content on success. A missing id makes
    // the service throw ResourceNotFoundException, which GlobalExceptionHandler
    // turns into a structured 404 -- so the controller only states the happy path.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewById(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
        return ResponseEntity.noContent().build();
    }
}
