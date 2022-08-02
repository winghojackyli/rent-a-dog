package com.example.rentadog.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private String review;
    private Long ownerId;

    public Review(String fullName, String review){
        this.fullName = fullName;
        this.review = review;
    }

    public Review(String fullName, String review, Long ownerId) {
        this.fullName = fullName;
        this.review = review;
        this.ownerId = ownerId;
    }
}
