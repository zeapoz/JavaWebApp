package com.webapp.movieapp.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String showUserList(Model model) {
        List<AppUser> userList = userService.listAll();
        model.addAttribute("userList", userList);
        return "users";
    }

    @GetMapping("/users/delete")
    public String deleteUser(@RequestParam Long id) {
        try {
            userService.deleteUserById(id);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/login?logout";
    }
}
