package com.example.hackathonmirea.controllers;

import com.example.hackathonmirea.utils.FormulaSemanticComparator;
import Database.DatabaseHandler;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/formulas")
public class FormulaController {
    private final DatabaseHandler dbHandler = new DatabaseHandler();
    private final FormulaSemanticComparator semanticComparator = new FormulaSemanticComparator();

    public FormulaController() {
        dbHandler.initializeDatabase();
    }

    @PostMapping("/saveFormula")
    public Map<String, Object> saveFormula(@RequestBody Map<String, String> body) {
        String formula = body.get("formula");
        Map<String, Object> response = new HashMap<>();

        if (formula == null || formula.trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "Формула LaTeX не введена.");
            return response;
        }

        // Получаем все формулы из базы данных
        List<String> existingFormulas = dbHandler.getAllFormulas();

        // Проверяем на семантическое совпадение
        for (String dbFormula : existingFormulas) {
            if (semanticComparator.areSemanticallyEqual(formula, dbFormula)) {
                response.put("status", "similar");
                response.put("message", "Похожие формулы найдены.");
                response.put("similarFormula", dbFormula);
                return response;
            }
        }

        // Если семантических совпадений нет, сохраняем формулу
        dbHandler.addFormula(formula);
        response.put("status", "success");
        response.put("message", "Формула сохранена!");
        return response;
    }


    @GetMapping("/getFormulas")
    public List<String> getFormulas() {
        return dbHandler.getAllFormulas();
    }
}
