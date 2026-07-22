package com.example.UberReviewService.services;

import com.example.UberReviewService.dto.CreateReviewRequest;
import com.example.UberEntityService.entity.BookingReview;
import com.example.UberEntityService.entity.PassengerReview;
import com.example.UberEntityService.entity.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    public Optional<Review> findReviewById(Long id);

    public List<Review> findAllReviews();

    // Deletes the review, or throws ResourceNotFoundException if no review has
    // this id (so the 404 flows through GlobalExceptionHandler like every other).
    public void deleteReviewById(Long id);

    // Review is abstract, so creation is per concrete subtype. Each needs an
    // existing booking (looked up from request.bookingId()).
    public BookingReview createBookingReview(CreateReviewRequest request);

    public PassengerReview createPassengerReview(CreateReviewRequest request);
}
