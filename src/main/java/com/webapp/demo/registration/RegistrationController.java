package com.webapp.demo.registration;

import com.webapp.demo.models.user.AppUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/register")
    public String showPage(Model model) {
        model.addAttribute("appUser", new AppUser());
        return "register";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "login";
    }

    @PostMapping("/register/save")
    public String saveUser(AppUser appUser) {
        registrationService.register(new RegistrationRequest(
            appUser.getUsername(),
            appUser.getPassword(),
            appUser.getEmail()));
        return "redirect:/";
    }
}
