package com.webapp.movieapp.store;

import java.util.List;

import com.webapp.movieapp.movie.Movie;
import com.webapp.movieapp.movie.MovieNotFoundException;
import com.webapp.movieapp.movie.MovieService;
import com.webapp.movieapp.user.AppUser;
import com.webapp.movieapp.user.UserRepository;
import com.webapp.movieapp.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StoreController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/store")
    public String showStore(Model model, @AuthenticationPrincipal AppUser user) {
        List<Movie> movieList = movieService.listAll();
        List<Movie> userMovies = (List<Movie>) userService.getUserMovies(user.getEmail());
        movieList.removeAll(userMovies);
        model.addAttribute("movieList", movieList);
        return "store";
    }

    @GetMapping("/store/buy")
    public String buyMovie(@RequestParam Long id, @AuthenticationPrincipal AppUser user) {
        try {
            Movie movie = movieService.getMovieById(id);
            List<Movie> movies = movieService.listAll();
            user.setMovies(movies);
            userRepository.save(user);
        } catch (MovieNotFoundException e) {
            e.printStackTrace();
        }
        return "store";
    }
}
