package com.example.UberReviewService.services;

import com.example.UberReviewService.entities.*;
import com.example.UberReviewService.repositories.BookingRepository;
import com.example.UberReviewService.repositories.DriverRepository;
import com.example.UberReviewService.repositories.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

        Optional<Driver> driver = driverRepository.hqlfindByIdAndLicenceNumber(1L, "DL1212");

        if(driver.isPresent()) {
            System.out.println(driver.get().getName());
        }
    }
}
