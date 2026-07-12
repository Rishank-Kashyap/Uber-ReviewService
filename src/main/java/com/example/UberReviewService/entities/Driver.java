package com.example.UberReviewService.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Driver extends BaseEntity {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;

    private String city;
    private String state;
    private String zip;
    private String country;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Builder.Default
    // mappedBy marks this as the inverse (non-owning) side of the relationship.
    // "driver" is the name of the @ManyToOne field in Booking (the owning side)
    // that actually holds the foreign key. This tells JPA the FK is managed there,
    // so it won't create a separate join column/table for this side.
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<>();
}
