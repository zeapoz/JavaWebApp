package com.webapp.movieapp.movie;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.webapp.movieapp.user.AppUser;

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
    private double criticRating;
    private double userRating;

    @ManyToMany(mappedBy = "movies", cascade = CascadeType.REMOVE)
    private Collection<AppUser> users;

    public Movie(
            String title,
            String description,
            String imagePath,
            int releaseYear,
            double price,
            double criticRating,
            double userRating) {
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.releaseYear = releaseYear;
        this.price = price;
        this.criticRating = criticRating;
        this.userRating = userRating;
    }
}
