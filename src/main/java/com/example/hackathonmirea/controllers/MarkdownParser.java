package com.example.hackathonmirea.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownParser {
    public void parseMarkdownAndSaveFormulas(String mdFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(mdFilePath))) {
            String line;
            StringBuilder sb = new StringBuilder();

            // Читаем файл построчно
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            String text = sb.toString();  // Все строки в один текст

            // Регулярка для формул в "блоке", содержащих "="
            String blockRegex = "\\${2}(?=.*=)([\\s\\S]*?)\\${2}";
            Pattern blockPattern = Pattern.compile(blockRegex);
            Matcher blockMatcher = blockPattern.matcher(text);

            System.out.println("Формулы в блоках (с =):");
            while (blockMatcher.find()) {
                String formula = blockMatcher.group(1).trim();
                if (!formula.isEmpty()) {  // Проверяем, что строка не пустая
                    System.out.println(formula);
                }
            }

            // Регулярка для формул в строке, содержащих "="
            String inlineRegex = "\\$(?=.*=)(.*?)\\$";
            Pattern inlinePattern = Pattern.compile(inlineRegex);
            Matcher inlineMatcher = inlinePattern.matcher(text);

            System.out.println("Формулы в строках (с =):");
            while (inlineMatcher.find()) {
                String formula = inlineMatcher.group(1).trim();
                if (!formula.isEmpty()) {  // Проверяем, что строка не пустая
                    System.out.println(formula);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка при обработке Markdown файла.");
        }
    }
}
