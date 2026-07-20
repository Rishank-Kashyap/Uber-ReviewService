package com.example.UberReviewService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// Request body for creating a review. We take a bookingId (not a whole Booking)
// because every review requires an existing booking (review.booking_id is NOT NULL).
public class CreateReviewRequest{
    private String content;
    private Double rating;
    private Long bookingId;
}
