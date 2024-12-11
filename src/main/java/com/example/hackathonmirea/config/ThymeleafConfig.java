package com.example.hackathonmirea.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.springframework.context.ApplicationContext;

@Configuration
public class ThymeleafConfig {

    @Bean
    public SpringResourceTemplateResolver templateResolver(ApplicationContext applicationContext) {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);  // Устанавливаем контекст приложения
        resolver.setPrefix("classpath:/templates/"); // Путь к шаблонам
        resolver.setSuffix(".html");  // Расширение шаблонов
        resolver.setTemplateMode(TemplateMode.HTML); // Формат шаблонов
        resolver.setCharacterEncoding("UTF-8");  // Кодировка
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver); // Устанавливаем наш templateResolver
        engine.setEnableSpringELCompiler(true);  // Включение Spring Expression Language
        return engine;
    }
}