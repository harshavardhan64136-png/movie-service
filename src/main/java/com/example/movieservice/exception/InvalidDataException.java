package com.example.movieservice.exception;

public class InvalidDataException extends RuntimeException{

    public InvalidDataException(String message){
        super("message");
    }

}
