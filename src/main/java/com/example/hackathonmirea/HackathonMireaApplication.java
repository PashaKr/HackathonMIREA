package com.example.hackathonmirea;

import com.example.hackathonmirea.controllers.MarkdownParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HackathonMireaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HackathonMireaApplication.class, args);
//        //ПАРСЕР .md
//        MarkdownParser parser = new MarkdownParser();
//            parser.parseMarkdownAndSaveFormulas("/Users/daniilzarkov/Documents/Obsidian Vault/Универ/Задания всякие/Теория ряды.md");
    }
}
