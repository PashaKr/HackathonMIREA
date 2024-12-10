package com.example.hackathonmirea.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Database.DatabaseHandler;

public class MarkdownParser {

    // Регулярное выражение для поиска формул между $$ в Markdown
    private static final String LATEX_PATTERN = "(?<=\\$\\$)(.*?)(?=\\$\\$)";
    // Регулярное выражение для поиска русских букв
    private static final String RUSSIAN_PATTERN = "[а-яА-Я]";

    public void parseMarkdownAndSaveFormulas(String mdFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(mdFilePath))) {
            String line;
            StringBuilder sb = new StringBuilder();

            // Читаем файл построчно
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            String text = sb.toString();  // Все строки в один текст
            System.out.println("Извлеченный текст: " + text);  // Выводим текст для проверки

            // Поиск формул с использованием регулярного выражения
            Pattern pattern = Pattern.compile(LATEX_PATTERN);
            Matcher matcher = pattern.matcher(text);

            DatabaseHandler dbHandler = new DatabaseHandler();

            // Обработка всех найденных формул
            while (matcher.find()) {
                String formula = matcher.group().trim();

                // Пропуск формул, содержащих русские буквы
                if (!formula.isEmpty() && !containsRussianLetters(formula)) {
                    System.out.println("Найдена формула: " + formula);  // Выводим формулу для проверки
                    // Добавление формулы в базу данных
                    dbHandler.addFormula(formula);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка при обработке Markdown файла.");
        }
    }

    // Метод для проверки наличия русских букв в формуле
    private boolean containsRussianLetters(String formula) {
        Pattern pattern = Pattern.compile(RUSSIAN_PATTERN);
        Matcher matcher = pattern.matcher(formula);
        return matcher.find();
    }

}
