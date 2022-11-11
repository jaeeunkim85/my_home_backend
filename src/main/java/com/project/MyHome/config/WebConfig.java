package com.project.MyHome.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "OPTIONS", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("Access-Control-Allow-Origin","Access-Control-Allow-Credentials")
                .allowCredentials(true).maxAge(10);
    }
}
