package com.webapp.movieapp.movie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movie")
    public String showMovieDetails(@RequestParam Long id, Model model) {
        try {
            Movie movie = movieService.getMovieById(id);
            model.addAttribute("movie", movie);
            return "moviedetails";
        } catch (MovieNotFoundException e) {
            return "redirect:/movies";
        }
    }

    @GetMapping("/movies")
    public String showMovieList(Model model) {
        List<Movie> movieList = movieService.listAll();
        model.addAttribute("movieList", movieList);
        return "movies";
    }

    @GetMapping("/movies/new")
    public String showNewForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "movieform";
    }

    @GetMapping("/movies/edit")
    public String showEditForm(@RequestParam Long id, Model model) {
        try {
            Movie movie = movieService.getMovieById(id);
            model.addAttribute("movie", movie);
            return "movieform";
        } catch (MovieNotFoundException e) {
            return "movies";
        }
    }

    @GetMapping("/movies/delete")
    public String deleteMovie(@RequestParam Long id) {
        try {
            movieService.deleteMovieById(id);
        } catch (MovieNotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/login?logout";
    }

    @PostMapping("/movies/save")
    public String saveMovie(Movie movie) {
        movieService.save(movie);
        return "redirect:/movies";
    }
}
