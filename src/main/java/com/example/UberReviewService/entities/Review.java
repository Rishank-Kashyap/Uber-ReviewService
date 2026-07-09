package com.example.UberReviewService.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bookingReview")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Review extends BaseEntity {
    @Column(nullable = false)
    private String content;

    private Double rating;

    @Override
    public String toString() {
        return "Review:" + content + ", rating:" + rating + ", createdAt:" + this.createdAt;
    }
}
