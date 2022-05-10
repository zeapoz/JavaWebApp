package com.webapp.movieapp.genre;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> listAll() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(Long id) throws GenreNotFoundException {
        Optional<Genre> Genre = genreRepository.findById(id);
        if (Genre.isPresent()) {
            return Genre.get();
        }
        throw new GenreNotFoundException("No genre found with id " + id);
    }

    public void deleteGenreById(Long id) throws GenreNotFoundException {
        Optional<Genre> Genre = genreRepository.findById(id);
        if (Genre.isPresent()) {
            genreRepository.deleteById(id);
        }
        throw new GenreNotFoundException("No genre found with id " + id);
    }

    public void save(Genre genre) {
        genreRepository.save(genre);
    }
}
