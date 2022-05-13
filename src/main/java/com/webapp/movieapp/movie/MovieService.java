package com.webapp.movieapp.movie;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import com.webapp.movieapp.user.AppUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public List<Movie> listAll() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) throws MovieNotFoundException {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            return optionalMovie.get();
        } else {
            throw new MovieNotFoundException("No movie found with id " + id);
        }
    }

    @Transactional
    public void deleteMovieById(Long id) throws MovieNotFoundException {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            // movieRepository.deleteById(id);
            EntityManager em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();

            try {
                Movie m = em.find(Movie.class, id);
                for (AppUser user : m.getUsers()) {
                    user.getMovies().remove(m);
                }
                em.remove(m);
                em.getTransaction().commit();
                em.flush();
                em.clear();
            } catch (Exception e) {
                return;
            }
        } else {
            throw new MovieNotFoundException("No movie found with id " + id);
        }
    }

    public void save(Movie movie) {
        movieRepository.save(movie);
    }
}
