package com.example.UberReviewService.repositories;

import com.example.UberReviewService.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Integer countAllByRatingIsLessThanEqual(Double givenRating);

    List<Review> findAllByRatingIsLessThanEqual(Double givenRating);

    List<Review> findAllByCreatedAtBefore(Date givenDate);

    // JPQL navigates entity fields, not tables. The relationship is now
    // unidirectional (Review owns `booking`), so we find a booking's review by
    // querying the review whose booking has that id -- r.booking.id. Selecting
    // FROM the concrete subtype (BookingReview / PassengerReview) filters by type.
    @Query("SELECT r FROM BookingReview r WHERE r.booking.id = :bookingId")
    Review findBookingReviewByBookingId(Long bookingId);

    @Query("SELECT r FROM PassengerReview r WHERE r.booking.id = :bookingId")
    Review findPassengerReviewByBookingId(Long bookingId);

}
