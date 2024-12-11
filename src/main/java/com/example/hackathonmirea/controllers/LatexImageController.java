package com.example.hackathonmirea.controllers;

import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@RestController
@RequestMapping("/api")
public class LatexImageController {

    @PostMapping("/save-image")
    public ResponseEntity<String> saveLatexImage(@RequestBody LatexRequest request) {
        try {
            // Проверка: пустая ли форма с формулой
            if (request.getLatex() == null || request.getLatex().trim().isEmpty()) {
                throw new IllegalArgumentException("Формула LaTeX не введена.");
            }

            // Создание формулы из LaTeX
            TeXFormula formula = new TeXFormula(request.getLatex());
            TeXIcon icon = formula.createTeXIcon(TeXFormula.SERIF, 20);

            // Создание изображения
            BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = image.createGraphics();
            g2.setColor(Color.WHITE); // Фон
            g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
            icon.paintIcon(null, g2, 0, 0);

            // Путь к папке "Загрузки"
            String userHome = System.getProperty("user.home");
            File downloadsDir = new File(userHome, "Downloads");
            if (!downloadsDir.exists()) {
                downloadsDir.mkdirs(); // Создать папку Загрузки, если её нет
            }

            // Сохранение изображения в папку Загрузки
            File outputFile = new File(downloadsDir, "latex-image.png");
            ImageIO.write(image, "png", outputFile);

            return ResponseEntity.ok("Изображение сохранено в папке Загрузки: " + outputFile.getAbsolutePath());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Ошибка при сохранении изображения: " + e.getMessage());
        }
    }

    // Класс для запроса
    static class LatexRequest {
        private String latex;

        public String getLatex() {
            return latex;
        }

        public void setLatex(String latex) {
            this.latex = latex;
        }
    }
}
