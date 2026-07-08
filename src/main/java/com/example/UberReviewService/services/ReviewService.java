package com.example.UberReviewService.services;

import com.example.UberReviewService.entities.Review;
import com.example.UberReviewService.repositories.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService implements CommandLineRunner {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Review Service");

        Review r = Review.builder().content("This is a review").rating(5.0).build(); // code to create plain java objects
        reviewRepository.save(r); // this coed executes SQL queries

        List<Review> reviews = reviewRepository.findAll();
        for (Review review : reviews) {
            System.out.println(review.getContent());
        }
    }
}
