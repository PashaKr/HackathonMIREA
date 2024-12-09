package com.example.hackathonmirea.controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LatexController {

    @GetMapping("/")
    public String showLatexForm(Model model) {

        return "index";
    }
}
