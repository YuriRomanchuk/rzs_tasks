package com.ua.amazonua.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Веб-конфігурація.
 *
 * CORS дозволяє React dev-серверу (Vite, http://localhost:5173) ходити в наш API
 * з іншого порту. У проді (крок 6) запити підуть через nginx-проксі з того ж
 * хоста, і CORS буде не потрібен — але для локальної розробки він зручний.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
