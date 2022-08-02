package com.example.rentadog.service;

import com.example.rentadog.entities.Dog;
import com.example.rentadog.repositories.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

import static org.springframework.util.StringUtils.cleanPath;

@Service
public class DogService {
    @Autowired
    private DogRepository dogRepository;

    public DogService(DogRepository dogRepository){
        this.dogRepository=dogRepository;
    }

    //multipartfile lets you upload img to db
    public void saveDogToDB(String dogName, String ownerName, String email,
                            String location, String breed, char sex, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        Dog d = new Dog();
        String fileName1 = cleanPath(file1.getOriginalFilename());
        if(fileName1.contains("..")){
            System.out.println("Not a valid file");
        }
        try {
            d.setImage1(Base64.getEncoder().encodeToString(file1.getBytes())); //this encodes img to a string representation
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileName2 = cleanPath(file2.getOriginalFilename());
        if(fileName2.contains("..")){
            System.out.println("Not a valid file");
        }
        try {
            d.setImage2(Base64.getEncoder().encodeToString(file2.getBytes())); //this encodes img to a string representation
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileName3 = cleanPath(file3.getOriginalFilename());
        if(fileName3.contains("..")){
            System.out.println("Not a valid file");
        }
        try {
            d.setImage3(Base64.getEncoder().encodeToString(file3.getBytes())); //this encodes img to a string representation
        } catch (IOException e) {
            e.printStackTrace();
        }

        //set variables
        d.setDogName(dogName);
        d.setOwnerName(ownerName);
        d.setEmail(email);
        d.setLocation(location);
        d.setBreed(breed);
        d.setSex(sex);

        dogRepository.save(d);
    }

}
