package com.example.UberReviewService.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Booking extends BaseEntity {

    // Note: the Booking <-> Review relationship is UNIDIRECTIONAL. Review owns it
    // (Review.booking, FK review.booking_id NOT NULL), so a Booking holds no
    // reference back to its reviews. To fetch a booking's reviews, query the
    // review side by booking id (see ReviewRepository).

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    private Long totalDistance;

    // LAZY so loading a booking doesn't automatically pull the driver/passenger
    // rows too (@ManyToOne defaults to EAGER, unlike @OneToMany)
    @ManyToOne(fetch = FetchType.LAZY)
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    private Passenger passenger;
}
