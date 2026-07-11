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

    @OneToOne(cascade = CascadeType.ALL)
    private BookingReview bookingReview; // we have added 1:1 relationship between booking and bookingReview

    @OneToOne(cascade = CascadeType.ALL)
    private PassengerReview passengerReview;


    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    private Long totalDistance;
}
