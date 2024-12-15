package com.example.hackathonmirea.utils;

import java.util.*;
import java.util.regex.*;

public class FormulaSemanticComparator {

    public List<String> parseFormula(String formula) {
        String cleanedFormula = formula.replaceAll("[{}]", "").trim();

        List<String> tokens = new ArrayList<>();

        String regex = "\\\\[a-zA-Z]+|[a-zA-Z]|\\d+|\\W";

        Matcher matcher = Pattern.compile(regex).matcher(cleanedFormula);

        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        return tokens;
    }

    public boolean areSemanticallyEqual(String formula1, String formula2) {
        List<String> tokens1 = normalizeFormula(parseFormula(formula1));
        List<String> tokens2 = normalizeFormula(parseFormula(formula2));

        return tokens1.equals(tokens2);
    }

    private List<String> normalizeFormula(List<String> tokens) {
        Map<String, String> variableMapping = new HashMap<>();
        int variableCounter = 1;

        List<String> normalizedTokens = new ArrayList<>();
        for (String token : tokens) {
            if (isVariable(token)) {
                variableMapping.putIfAbsent(token, "x" + variableCounter++);
                normalizedTokens.add(variableMapping.get(token));
            } else {
                normalizedTokens.add(token);
            }
        }

        return normalizedTokens;
    }

    private boolean isVariable(String token) {
        return token.matches("^[a-zA-Z]$");
    }
}
