package com.example.UberReviewService.mappers;

import com.example.UberReviewService.dto.CreateReviewRequest;
import com.example.UberReviewService.entities.Booking;
import com.example.UberReviewService.entities.BookingReview;
import com.example.UberReviewService.entities.PassengerReview;
import org.springframework.stereotype.Component;

// Pure mapper: DTO -> entity, no DB access and no side effects (easy to test).
// The Booking is resolved by the service and passed in, so this class never
// touches a repository. Review is abstract, so we build the concrete subtype
// via each one's own @SuperBuilder (Review.builder() doesn't exist).
@Component
public class CreateReviewRequestMapper {

    public BookingReview toBookingReview(CreateReviewRequest request, Booking booking) {
        return BookingReview.builder()
                .content(request.getContent())
                .rating(request.getRating())
                .booking(booking)
                .build();
    }

    public PassengerReview toPassengerReview(CreateReviewRequest request, Booking booking) {
        return PassengerReview.builder()
                .content(request.getContent())
                .rating(request.getRating())
                .booking(booking)
                .build();
    }
}
