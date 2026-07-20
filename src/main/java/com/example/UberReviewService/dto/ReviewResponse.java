package com.example.UberReviewService.dto;

import com.example.UberReviewService.entities.Review;

// Response DTO. We return this instead of the Review entity so serialization
// doesn't follow the Review <-> Booking cycle (which would recurse infinitely).
// type is the concrete subtype name (BookingReview / PassengerReview).
public record ReviewResponse(Long id, String content, Double rating, Long bookingId, String type) {

    public static ReviewResponse from(Review review) {
        Long bookingId = review.getBooking() != null ? review.getBooking().getId() : null;
        return new ReviewResponse(
                review.getId(),
                review.getContent(),
                review.getRating(),
                bookingId,
                review.getClass().getSimpleName()
        );
    }
}
