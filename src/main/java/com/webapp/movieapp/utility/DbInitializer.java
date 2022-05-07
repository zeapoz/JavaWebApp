package com.webapp.movieapp.utility;

import java.util.Collections;

import com.webapp.movieapp.movie.Movie;
import com.webapp.movieapp.movie.MovieRepository;
import com.webapp.movieapp.user.AppUser;
import com.webapp.movieapp.user.UserRole;
import com.webapp.movieapp.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInitializer implements CommandLineRunner {

    @Autowired
    UserService userService;
    @Autowired
    MovieRepository movieRepository;

    @Override
    public void run(String... args) throws Exception {
        seedMovies();
        seedUsers();
    }

    private void seedUsers() {
        AppUser admin = new AppUser("admin", "admin", "admin@localhost", UserRole.ADMIN);
        AppUser user = new AppUser("user", "user", "user@localhost", UserRole.USER);

        admin.setEnabled(true);
        user.setEnabled(true);

        Movie test = new Movie();
        test.setTitle("Test");
        test.setDescription("This is a test movie");
        movieRepository.save(test);

        user.setMovies(Collections.singletonList(test));
        userService.signUpUser(admin);
        userService.signUpUser(user);
    }
    
    private void seedMovies() {
        createMovie(
            "Sonic the Hedgehog 2",
            "When the manic Dr Robotnik returns to Earth with a new ally, Knuckles the Echidna, Sonic and his new friend Tails is all that stands in their way.",
            "sonic.jpg",
            2022,
            20.0,
            4.1,
            4.3
        );
        createMovie(
            "Cars",
            "A hot-shot race-car named Lightning McQueen gets waylaid in Radiator Springs, where he finds the true meaning of friendship and family.",
            "cars.jpg",
            2006,
            24.0,
            4.1,
            4.3
        );
        createMovie(
            "Shrek",
            "A mean lord exiles fairytale creatures to the swamp of a grumpy ogre, who must go on a quest and rescue a princess for the lord in order to get his land back.",
            "shrek.jpg",
            2001,
            25.0,
            4.1,
            4.3
        );
        createMovie(
            "Kung Fu Panda",
            "To everyone's surprise, including his own, Po, an overweight, clumsy panda, is chosen as protector of the Valley of Peace. His suitability will soon be tested as the valley's arch-enemy is on his way.",
            "kungfupanda.jpg",
            2008,
            19.0,
            4.1,
            4.3
        );
        createMovie(
            "Despicable Me",
            "When a criminal mastermind uses a trio of orphan girls as pawns for a grand scheme, he finds their love is profoundly changing him for the better.",
            "despicableme.jpg",
            2010,
            21.0,
            4.1,
            4.3
        );
        createMovie(
            "The Emoji Movie",
            "Gene, a multi-expressional emoji, sets out on a journey to become a normal emoji.",
            "emoji.jpg",
            2017,
            33.0,
            5.0,
            5.0
        );
    }

    private void createMovie(
        String title,
        String description,
        String imagePath,
        int releaseYear,
        double price,
        double criticRating,
        double userRating
    ) {
        imagePath = "/images/" + imagePath;
        Movie movie = new Movie(title, description, imagePath, releaseYear, price, criticRating, userRating);
        movieRepository.save(movie);
    }
}
