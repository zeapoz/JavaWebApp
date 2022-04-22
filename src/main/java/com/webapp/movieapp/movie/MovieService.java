package com.webapp.movieapp.movie;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
   
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> listAll() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) throws MovieNotFoundException {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            return optionalMovie.get();
        }
        throw new MovieNotFoundException("No movie found with id " + id);
    }

    public void deleteMovieById(Long id) throws MovieNotFoundException {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            movieRepository.deleteById(id);
        }
        throw new MovieNotFoundException("No movie found with id " + id);
    }

    public void save(Movie movie) {
        movieRepository.save(movie);
    }
}
