package com.webapp.movieapp.user;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.webapp.movieapp.movie.Movie;
import com.webapp.movieapp.registration.token.ConfirmationToken;
import com.webapp.movieapp.registration.token.ConfirmationTokenService;
import com.webapp.movieapp.store.NotEnoughCreditsException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("No such email."));
    }

    public List<AppUser> listAll() {
        return userRepository.findAll();
    }

    public Set<Movie> getUserMovies(String username) {
        AppUser user = (AppUser) loadUserByUsername(username);
        return user.getMovies();
    }

    public void AddUserMovie(AppUser appUser, Movie movie) throws NotEnoughCreditsException {
        // Validate credits
        if (appUser.getCredits() < movie.getPrice()) {
            throw new NotEnoughCreditsException("not enough credits");
        }
        appUser.setCredits(appUser.getCredits() - movie.getPrice());

        // Add new movie to user movies
        Set<Movie> movies = new HashSet<>(appUser.getMovies());
        movies.add(movie);
        appUser.getMovies().clear();
        appUser.setMovies(movies);
        userRepository.save(appUser);
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = userRepository.findByEmail(appUser.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("Email already exists.");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        userRepository.save(appUser);

        // Create confirmation token
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return confirmationToken.getToken();
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

    public void deleteUserById(Long id) throws UserNotFoundException {
        Optional<AppUser> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            // userRepository.deleteById(id);
            EntityManager em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();

            AppUser u = em.find(AppUser.class, id);
            for (Movie movie : u.getMovies()) {
                movie.getUsers().remove(u);
            }
            em.remove(u);
            em.getTransaction().commit();
        } else {
            throw new UserNotFoundException("No user found with id " + id);
        }
    }
}
