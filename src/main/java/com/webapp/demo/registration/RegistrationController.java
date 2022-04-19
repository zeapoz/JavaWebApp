package com.webapp.demo.registration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {

    private RegistrationService registrationService;

    @GetMapping("/registration")
    public String showPage(Model model) {
        return "registration";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login";
    }
}
