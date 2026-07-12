package com.example.UberReviewService.repositories;

import com.example.UberReviewService.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByDriverId(Long driverId);
}
