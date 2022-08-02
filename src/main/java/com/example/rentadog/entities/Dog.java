package com.example.rentadog.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String dogName;
    private String ownerName;
    private String email;
    private String location;
    private String breed;
    private char sex;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image1;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image2;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image3;

}
