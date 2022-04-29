package com.webapp.movieapp.dashboard;

import java.util.List;

import com.webapp.movieapp.movie.Movie;
import com.webapp.movieapp.user.AppUser;
import com.webapp.movieapp.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/dashboard")
    public String showDashboard(Model model, @AuthenticationPrincipal AppUser user) {
        List<Movie> movieList = (List<Movie>) userService.getUserMovies(user.getEmail());
        model.addAttribute("movieList", movieList);
        return "dashboard";
    }

    @GetMapping("/dashboard/play")
    public String showPlayer(Model model) {
        return "player";
    }
}
