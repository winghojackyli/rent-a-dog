package com.example.rentadog.service;

import com.example.rentadog.entities.Review;
import com.example.rentadog.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository=reviewRepository;
    }
    public void saveReviewToDB(String fullName, String review, Long ownerId){
        Review r = new Review();
        r.setFullName(fullName);
        r.setReview(review);
        r.setOwnerId(ownerId);
        reviewRepository.save(r);
    }

}
