package com.webapp.movieapp.dashboard;

import java.util.List;

import com.webapp.movieapp.movie.Movie;
import com.webapp.movieapp.movie.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private MovieService movieService;
    
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<Movie> movieList = movieService.listAll();
        model.addAttribute("movieList", movieList);
        return "dashboard";
    }
}
