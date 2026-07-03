package com.example.movieservice.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.example.movieservice.model.Movie;
import com.example.movieservice.repo.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MovieRepository movieRepository;

    @BeforeEach
    void cleanUp(){
        movieRepository.deleteAllInBatch();
    }

    @Test
    void givenMovie_whenCreateMovie_thenReturnSaveMovie() throws Exception {
        //given
        Movie movie= new Movie();
        movie.setName("rrr");
        movie.setDirector("ss rajamouli");
        movie.setActors(List.of("ntr","ramcharan","alibhatt"));

        // when movie is create

     var response =   mockMvc.perform(post("/movies")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(movie)));

               // they verify save movie
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(notNullValue())))
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect(jsonPath("$.actors", is(movie.getActors())));




        

    }
    @Test
    void giveMovieId_whenFetchMovie_thenReturnMovie() throws Exception{

        Movie movie= new Movie();
        movie.setName("rrr");
        movie.setDirector("ss rajamouli");
        movie.setActors(List.of("ntr","ramcharan","alibhatt"));

        Movie savedMovie =   movieRepository.save(movie);


        var response = mockMvc.perform(get("/movies/"+savedMovie.getId()));

        response.andDo(print())
                .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(savedMovie.getId()))
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect(jsonPath("$.actors", is(movie.getActors())));
    }
    @Test
    void  givenSavedMovie_WhenUpdateMovie_thenMovieUpdateInDb() throws Exception{

         Movie movie= new Movie();
        movie.setName("rrr");
        movie.setDirector("ss rajamouli");
        movie.setActors(List.of("ntr","ramcharan","alibhatt"));

        Movie savedMovie =   movieRepository.save(movie);
        Long id =savedMovie.getId();
        
        //update
        movie.setActors(List.of("ntr","ramcharan","alibhatt","ajaydevgan"));

          var response =   mockMvc.perform(put("/movies/"+ id)
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(movie)));

                response.andDo(print())
                .andExpect(status().isOk());
        

          var fetchresponse = mockMvc.perform(get("/movies/"+id));

        fetchresponse.andDo(print())
                .andExpect(status().isOk())
              
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect(jsonPath("$.actors", is(movie.getActors())));

    }
    @Test
void givenMovie_whenDeleteRequest_thenMovieRemovedFromDb() throws Exception{
     Movie movie= new Movie();
        movie.setName("rrr");
        movie.setDirector("ss rajamouli");
        movie.setActors(List.of("ntr","ramcharan","alibhatt"));

        Movie savedMovie =   movieRepository.save(movie);
        Long id =savedMovie.getId();

        mockMvc.perform(delete("/movies/"+id))
        .andDo(print())
        .andExpect(status().isOk());

        assertFalse(movieRepository.findById(id).isPresent());



}

}