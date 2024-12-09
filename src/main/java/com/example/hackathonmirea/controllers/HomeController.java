package com.example.hackathonmirea.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // Отображение формы
    }

    @PostMapping("/render")
    public String renderLatex(@RequestParam("latexInput") String latexInput, Model model) {
        model.addAttribute("latexInput", latexInput);
        return "index"; // Возвращаем ту же страницу с визуализацией формулы
    }
}
