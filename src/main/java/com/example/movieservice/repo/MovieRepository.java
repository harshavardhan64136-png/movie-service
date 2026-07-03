package com.example.movieservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movieservice.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

}
