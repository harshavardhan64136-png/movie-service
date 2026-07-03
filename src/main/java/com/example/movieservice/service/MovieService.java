package com.example.movieservice.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.movieservice.exception.InvalidDataException;
import com.example.movieservice.exception.NotFoundException;
import com.example.movieservice.model.Movie;
import com.example.movieservice.repo.MovieRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MovieService {
    
    @Autowired
    private MovieRepository movieRepository;

    public Movie create(Movie movie){
        if(movie==null){
            throw new InvalidDataException("invaild movie null");
        }
         return movieRepository.save(movie);
    }
    public Movie read(Long id){

       return  movieRepository.findById(id).orElseThrow(()->new NotFoundException("movie not fount with id" +id));
    }
   public Movie update(Long id,Movie update) {
    if(update == null || id==null){
        throw new InvalidDataException("invaild movie");
    }if(movieRepository.existsById(id)){
        Movie movie = movieRepository.getReferenceById(id);
        movie.setName(update.getName());
        movie.setDirector(update.getDirector());
        movie.setActors(update.getActors());
       return  movieRepository.save(movie);
    }else{
        throw new NotFoundException("movie not fount with id" +id);
    }

   
}
   public void delete(Long id){
    if(movieRepository.existsById(id)){
        movieRepository.deleteById(id);
    }else{
        throw new NotFoundException("movie not found with id" + id);
    }
   }
   

}
