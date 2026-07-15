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

    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private BookingReview bookingReview; // we have added 1:1 relationship between booking and bookingReview

    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private PassengerReview passengerReview;


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
