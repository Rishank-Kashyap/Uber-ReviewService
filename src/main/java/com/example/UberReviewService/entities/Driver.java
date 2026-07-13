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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    // N+1 fix, APPROACH 1: @Fetch(FetchMode.SUBSELECT)
    // Problem: loading many drivers and then calling getBookings() on each one
    // fires a separate query PER driver -> 1 (drivers) + N (bookings) = N+1.
    //
    // With SUBSELECT, the FIRST getBookings() access loads the bookings for ALL
    // drivers from the original query in ONE query, by re-running that query as a
    // subselect:
    //   select * from booking where driver_id in (select id from driver where <original where>)
    // Hibernate then populates EVERY driver's bookings collection, so the rest of
    // the loop is served from the persistence context (no more queries).
    //   => total = 2 queries, and Hibernate does the per-driver mapping for us.
    //
    // Note: only the drivers selected by the original query are fetched (not the
    // whole table), and getBookings() must be called inside an open session
    // (a @Transactional method). Contrast with APPROACH 2 in ReviewService.
    @Fetch(FetchMode.SUBSELECT)
    private List<Booking> bookings = new ArrayList<>();
}
