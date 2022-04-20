package com.webapp.demo.utility;

import com.webapp.demo.models.movie.Movie;
import com.webapp.demo.models.movie.MovieRepository;
import com.webapp.demo.models.user.AppUser;
import com.webapp.demo.models.user.UserRole;
import com.webapp.demo.models.user.UserService;

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
        seedUsers();
        seedMovies();
    }

    private void seedUsers() {
        AppUser admin = new AppUser("admin", "admin", "admin@localhost", UserRole.ADMIN);
        userService.signUpUser(admin);
    }
    
    private void seedMovies() {
        createMovie("Sonic the Hedgehog 2", "When the manic Dr Robotnik returns to Earth with a new ally, Knuckles the Echidna, Sonic and his new friend Tails is all that stands in their way.", 2022, 20.0);
        createMovie("Cars", "A hot-shot race-car named Lightning McQueen gets waylaid in Radiator Springs, where he finds the true meaning of friendship and family.", 2006, 24.0);
    }

    private void createMovie(
        String title,
        String description,
        int releaseYear,
        double price
    ) {
        Movie movie = new Movie(title, description, releaseYear, price);
        movieRepository.save(movie);
    }
}
