package com.webapp.movieapp.store;

public class NotEnoughCreditsExeption extends Throwable {
    
    public NotEnoughCreditsExeption(String message) {
        super(message);
    }
}
