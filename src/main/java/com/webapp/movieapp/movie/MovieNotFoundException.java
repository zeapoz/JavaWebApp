package com.webapp.movieapp.movie;

public class MovieNotFoundException extends Throwable {

    public MovieNotFoundException(String message) {
        super(message);
    }
}
