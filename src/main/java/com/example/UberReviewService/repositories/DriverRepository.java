package com.example.UberReviewService.repositories;

import com.example.UberReviewService.entities.Booking;
import com.example.UberReviewService.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByIdAndLicenseNumber(Long id, String licenseNumber);

    // Native SQL: sent to the database verbatim and NOT validated by Hibernate,
    // so mistakes (wrong column/table, bad syntax) only surface at RUNTIME when
    // the query is executed. Test these manually.
    @Query(nativeQuery = true, value = "SELECT * FROM Driver WHERE id = :id AND license_number = :licenseNumber")
    Optional<Driver> rawfindByIdAndLicenceNumber(Long id, String licenseNumber);

    // HQL/JPQL: Hibernate parses and validates it against the entity model at
    // application STARTUP (not Java compile time), so an invalid query fails on
    // boot -- before it is ever called -- rather than only when executed.
    @Query("SELECT d FROM Driver d where d.id = :id AND d.licenseNumber = :licenseNumber")
    Optional<Driver> hqlfindByIdAndLicenceNumber(Long id, String licenseNumber);

}
