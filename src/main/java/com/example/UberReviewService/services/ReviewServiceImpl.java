package com.example.UberReviewService.services;

import com.example.UberReviewService.dto.CreateReviewRequest;
import com.example.UberEntityService.entity.Booking;
import com.example.UberEntityService.entity.BookingReview;
import com.example.UberEntityService.entity.PassengerReview;
import com.example.UberEntityService.entity.Review;
import com.example.UberReviewService.exceptions.ResourceNotFoundException;
import com.example.UberReviewService.mappers.CreateReviewRequestMapper;
import com.example.UberReviewService.repositories.BookingRepository;
import com.example.UberReviewService.repositories.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final CreateReviewRequestMapper createReviewRequestMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             BookingRepository bookingRepository,
                             CreateReviewRequestMapper createReviewRequestMapper) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
        this.createReviewRequestMapper = createReviewRequestMapper;
    }

    @Override
    public Optional<Review> findReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteReviewById(Long id) {
        // Fetch first so a missing id becomes a clean ResourceNotFoundException
        // (-> 404 via the advice) instead of being swallowed. Fetching and
        // deleting in one @Transactional keeps the entity managed, so delete()
        // removes it directly without a re-lookup. Mirrors findBookingOrThrow.
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        reviewRepository.delete(review);
    }

    @Override
    @Transactional
    public BookingReview createBookingReview(CreateReviewRequest request) {
        Booking booking = findBookingOrThrow(request.getBookingId());
        BookingReview review = createReviewRequestMapper.toBookingReview(request, booking);
        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public PassengerReview createPassengerReview(CreateReviewRequest request) {
        Booking booking = findBookingOrThrow(request.getBookingId());
        PassengerReview review = createReviewRequestMapper.toPassengerReview(request, booking);
        return reviewRepository.save(review);
    }

    // A review requires an existing booking (booking_id is NOT NULL). The lookup
    // lives here in the service -- not in the mapper -- because DB access is
    // business logic, not mapping. Kept inside the @Transactional create methods
    // so the fetched Booking stays managed while the review is saved.
    private Booking findBookingOrThrow(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
    }
}
