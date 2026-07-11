package com.example.UberReviewService.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Passenger extends BaseEntity {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    @Builder.Default
    // mappedBy marks this as the inverse (non-owning) side of the relationship.
    // "passenger" is the name of the @ManyToOne field in Booking (the owning side)
    // that actually holds the foreign key. This tells JPA the FK is managed there,
    // so it won't create a separate join column/table for this side.
    @OneToMany(mappedBy = "passenger")
    private List<Booking> bookings = new ArrayList<>();
}
