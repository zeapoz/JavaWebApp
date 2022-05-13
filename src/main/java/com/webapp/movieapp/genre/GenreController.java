package com.webapp.movieapp.genre;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GenreController {

    @Autowired
    GenreService genreService;

    @GetMapping("/genres")
    public String showMovieList(Model model) {
        List<Genre> genreList = genreService.listAll();
        model.addAttribute("genreList", genreList);
        return "genres";
    }

    @GetMapping("/genres/new")
    public String showNewForm(Model model) {
        model.addAttribute("genre", new Genre());
        return "genreform";
    }

    @GetMapping("/genres/edit")
    public String showEditForm(@RequestParam Long id, Model model) {
        try {
            Genre genre = genreService.getGenreById(id);
            model.addAttribute("genre", genre);
            return "genreform";
        } catch (GenreNotFoundException e) {
            return "genres";
        }
    }

    @GetMapping("/genres/delete")
    public String deleteMovie(@RequestParam Long id) {
        try {
            genreService.deleteGenreById(id);
        } catch (GenreNotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/login?logout";
    }

    @PostMapping("/genres/save")
    public String saveMovie(Genre genre) {
        genreService.save(genre);
        return "redirect:/genres";
    }
}
