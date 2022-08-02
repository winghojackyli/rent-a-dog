package com.example.rentadog.repositories;

import com.example.rentadog.entities.Dog;
import com.example.rentadog.web.DogController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class Test2 {
    @InjectMocks
    DogController dogController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DogRepository dogRepository=mock(DogRepository.class);

    @Mock
    View mockView;

    @BeforeEach
    void ineach() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(dogController).setSingleView(mockView).build();
    }


    @Test
    void finddogbybreedtest() throws Exception {
        Dog d=new Dog();
        d.setBreed("Poodle");
        List<Dog> doglist = new ArrayList<>();
        doglist.add(d);

        when(dogRepository.findDogByBreed("Poodle")).thenReturn(doglist);

        mockMvc.perform(get("/index").param("keyword", "Poodle"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("listDogs", doglist))
                .andExpect(view().name("index"))
                .andExpect(model().attribute("listDogs", hasSize(1)));

        verify(dogRepository, times(1)).findDogByBreed(anyString());
        verifyNoMoreInteractions(dogRepository);
    }

}