package com.example.hackathonmirea.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // Отображение формы
    }
}
