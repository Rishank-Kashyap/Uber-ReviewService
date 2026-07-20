package com.example.UberReviewService.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder //superbuilder passes through the heirarchy to build the objects and it doesn't require AllArgsConstructor because on a field less class it can collide wit NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Review extends BaseEntity {
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Double rating;

    // Owning side of a UNIDIRECTIONAL 1:1 with Booking (FK: review.booking_id,
    // NOT NULL). No cascade on purpose: a review must never create or delete its
    // booking -- cascades flow parent -> child (Booking -> Review), never the
    // reverse. The booking already exists; the review just points at it.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Booking booking;

}
