package com.example.tasktodoapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    @GetMapping("/")
    public String register() {
        return "redirect:/lists/list";
    }

    @GetMapping("/login")
    public String showMyLoginPage() {

        return "login/login";

    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "login/accessDenied";

    }

}