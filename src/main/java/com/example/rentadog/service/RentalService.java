package com.example.rentadog.service;

import com.example.rentadog.entities.Rental;
import com.example.rentadog.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository){
        this.rentalRepository=rentalRepository;
    }
    public void saveRentalToDB(String fullName, String email, LocalDateTime startRental, LocalDateTime endRental, String message, String ownerEmail){
        Rental r = new Rental();

        r.setFullName(fullName);
        r.setEmail(email);
        r.setStartRental(startRental);
        r.setEndRental(endRental);
        r.setMessage(message);
        r.setOwnerEmail(ownerEmail);

        rentalRepository.save(r);
    }
}
