package com.webapp.movieapp.movie;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String imagePath;
    private int releaseYear;
    private double price;

    public Movie(String title, String description, String imagePath, int releaseYear, double price) {
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.releaseYear = releaseYear;
        this.price = price;
    }
}
