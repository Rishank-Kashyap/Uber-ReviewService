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
}
