package com.webapp.demo.models.movie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public String showMovieList(Model model) {
        List<Movie> movieList = movieService.listAll();
        model.addAttribute("movieList", movieList);
        return "movies";
    }
}
