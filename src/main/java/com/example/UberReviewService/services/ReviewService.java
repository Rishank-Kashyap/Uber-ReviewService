package com.example.UberReviewService.services;

import com.example.UberReviewService.entities.*;
import com.example.UberReviewService.repositories.BookingRepository;
import com.example.UberReviewService.repositories.DriverRepository;
import com.example.UberReviewService.repositories.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReviewService implements CommandLineRunner {

    private final ReviewRepository reviewRepository;

    private final BookingRepository bookingRepository;

    private final DriverRepository driverRepository;

    public ReviewService(ReviewRepository reviewRepository, BookingRepository bookingRepository,  DriverRepository driverRepository) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("Review Service");

//        BookingReview r = BookingReview.builder().content("This is a booking review").rating(5.0).build(); // code to create plain java objects
//
//        PassengerReview p = PassengerReview.builder().content("This is a passenger review").rating(5.0).build();
//
//        Booking b = Booking.builder().bookingStatus(BookingStatus.COMPLETED).bookingReview(r).passengerReview(p).startTime(new Date()).endTime(new Date()).build();
//
//        bookingRepository.save(b);
//
//        List<Review> reviews = reviewRepository.findAll();
//        for (Review review : reviews) {
//            System.out.println(review.getContent());
//        }

//        Optional<Driver> driver = driverRepository.findById(1L);
//
//        if(driver.isPresent()) {
//            System.out.println(driver.get().getName());
//            List<Booking> bookings = driver.get().getBookings();
//            for (Booking booking : bookings) {
//                System.out.println(booking.getDriver().getName());
//            }
//            List<Booking> bookings = bookingRepository.findAllByDriverId(1L);
//            for(Booking booking : bookings) {
//                System.out.println(booking.getBookingStatus());
//            }
//        }
//        Optional<Booking> b = bookingRepository.findById(1L);

//        Optional<Driver> driver = driverRepository.hqlfindByIdAndLicenceNumber(1L, "DL1212");
//
//        if(driver.isPresent()) {
//            System.out.println(driver.get().getName());
//        }

        // Getting many drivers' bookings without N+1
        // Two ways to avoid firing one bookings query PER driver. Both fetch ONLY the
        // selected drivers' bookings (same scope) and both cost 2 queries -- the only
        // difference is WHO maps the bookings back to each driver.
        //
        // APPROACH 1 - Hibernate FetchMode.SUBSELECT (see Driver.bookings):
        //   Load the drivers, then loop and call driver.getBookings(). The first
        //   access triggers ONE subselect that loads bookings for all loaded drivers,
        //   and Hibernate AUTO-POPULATES each driver's collection for us.
        //   Needs @Fetch(FetchMode.SUBSELECT) enabled + a @Transactional method.
        //
        // APPROACH 2 - manual 2-query + in-memory mapping:
        //   Query 1: findAllByIdIn(driverIds)   -> the drivers
        //   Query 2: findAllByDriverIn(drivers) -> a FLAT list of their bookings
        //            (select * from booking where driver_id in (...))
        //   Then WE group that flat list per driver ourselves (e.g. groupingBy driverId).
        //

        List<Long> driverIds = new ArrayList<>(Arrays.asList(1L, 2L, 6L, 7L, 8L));
        List<Driver> drivers = driverRepository.findAllByIdIn(driverIds);

        // APPROACH 2: fetch all bookings for these drivers in one query, then map/group
        // them per driver ourselves in memory.
        // List<Booking> bookings = bookingRepository.findAllByDriverIn(drivers);

        // APPROACH 1: getBookings() on the first driver loads ALL selected drivers'
        // bookings via a single subselect (requires @Fetch(FetchMode.SUBSELECT) enabled
        // on Driver.bookings); Hibernate maps them per driver, so this loop = 2 queries.
       for (Driver driver : drivers) {
           List<Booking> bookings = driver.getBookings();
           bookings.forEach(booking -> System.out.println(booking.getId()));
       }

    }
}
