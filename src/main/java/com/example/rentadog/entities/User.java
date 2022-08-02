package com.example.rentadog.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fName;
    private String username; // we will use email as username
    private String password;
    private String role;
    private boolean enabled;


    public User(String fName, String username, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.fName = fName;
        this.username = username;
        this.password = bCryptPasswordEncoder.encode(password);
    }



    @PrePersist
    public void prePersist() {
        role = "Customer";
        enabled = true;
    }

    public void setfName(String fName) {
    }
}
