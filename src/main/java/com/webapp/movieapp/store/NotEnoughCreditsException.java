package com.webapp.movieapp.store;

public class NotEnoughCreditsException extends Throwable {

    public NotEnoughCreditsException(String message) {
        super(message);
    }
}
