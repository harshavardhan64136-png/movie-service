package com.example.movieservice.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Movie {
    
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String director;
    
    @ElementCollection
    private List<String> actors = new ArrayList<>();


}
