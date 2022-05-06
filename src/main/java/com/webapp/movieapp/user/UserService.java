package com.webapp.movieapp.user;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.webapp.movieapp.movie.Movie;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("No such email.")
            );
    }

    public List<AppUser> listAll() {
        return userRepository.findAll();
    }

    public Collection<Movie> getUserMovies(String username) {
        AppUser user = (AppUser) loadUserByUsername(username);
        return user.getMovies();
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = userRepository.findByEmail(appUser.getEmail()).isPresent();
        if (userExists) {
            throw new IllegalStateException("Email already exists.");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        userRepository.save(appUser);

        return "This totally works.";
    }

    public void deleteUserById(Long id) throws UserNotFoundException {
        Optional<AppUser> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
        }
        throw new UserNotFoundException("No user found with id " + id);
    }
}
