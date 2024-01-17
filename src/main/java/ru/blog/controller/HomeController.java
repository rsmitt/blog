package ru.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) Optional<String> error, Model model) {
        if (error.isPresent()) {
            model.addAttribute("error", "Invalid Credentials provided");
        }

        return "login";
    }
}
