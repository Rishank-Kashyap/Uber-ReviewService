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

    // JPQL works on ENTITIES and their fields, not tables: no SELECT *, and the
    // join is implicit through the b.bookingReview / b.passengerReview association
    // -- no ON clause needed.
    @Query("SELECT b.bookingReview FROM Booking b WHERE b.id = :bookingId")
    Review findBookingReviewByBookingId(Long bookingId);

    @Query("SELECT b.passengerReview FROM Booking b WHERE b.id = :bookingId")
    Review findPassengerReviewByBookingId(Long bookingId);

}
