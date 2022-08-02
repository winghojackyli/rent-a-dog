package com.example.rentadog.repositories;

import com.example.rentadog.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
//    added for review
    List<Review> findReviewByOwnerId(Long ownerId);
}
