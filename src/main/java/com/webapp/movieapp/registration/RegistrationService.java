package com.webapp.movieapp.registration;

import java.time.LocalDateTime;

import com.webapp.movieapp.registration.token.ConfirmationToken;
import com.webapp.movieapp.registration.token.ConfirmationTokenService;
import com.webapp.movieapp.user.AppUser;
import com.webapp.movieapp.user.UserRole;
import com.webapp.movieapp.user.UserService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {

    private UserService userService;
    private EmailValidator emailValidator;
    private ConfirmationTokenService confirmationTokenService;

    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
            .getToken(token)
            .orElseThrow(
                () -> new IllegalStateException("token not found")
            );
        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(
            confirmationToken.getUser().getEmail()
        );

        return "confirmed";
    }

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("Email not valid");
        }

        return userService.signUpUser(
            new AppUser(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                UserRole.USER
            )
        );
    }
}
