package com.example.hackathonmirea.controllers;

import Database.DatabaseHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/formulas")
public class FormulaController {
    private final DatabaseHandler dbHandler = new DatabaseHandler();

    // Инициализация базы данных
    public FormulaController() {
        dbHandler.initializeDatabase();  // Добавляем инициализацию базы данных при запуске контроллера
    }

    @PostMapping("/saveFormula")
    public String saveFormula(@RequestBody Map<String, String> body) {
        String formula = body.get("formula");

        // Проверка на пустую форму
        if (formula == null || formula.trim().isEmpty()) {
            return "Формула LaTeX не введена.";
        }

        // Проверяем, существует ли уже такая формула
        if (dbHandler.isFormulaExists(formula)) {
            return "Формула уже сохранена";
        } else {
            dbHandler.addFormula(formula); // Добавляем формулу в базу данных
            return "Формула сохранена!";
        }
    }

    @GetMapping("/getFormulas")
    public List<String> getFormulas() {
        return dbHandler.getAllFormulas();
    }
}
