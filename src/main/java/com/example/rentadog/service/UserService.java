package com.example.rentadog.service;

import com.example.rentadog.entities.User;
import com.example.rentadog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
//    public void saveUserToDB(String fName, String username,String password){
//        User u = new User();
//        u.setfName(fName);
//        u.setUsername(username);
//        password = bCryptPasswordEncoder.encode(password);
//        u.setPassword(password);
//        u.setRole("Customer");
//        u.setEnabled(true);
//
//        userRepository.save(u);
//    }

    public void saveUser(User user){
        userRepository.save(user);
    }


}
