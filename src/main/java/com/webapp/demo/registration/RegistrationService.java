package com.webapp.demo.registration;

import com.webapp.demo.models.user.AppUser;
import com.webapp.demo.models.user.UserRole;
import com.webapp.demo.models.user.UserService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {

    private UserService userService;
    private EmailValidator emailValidator;

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
