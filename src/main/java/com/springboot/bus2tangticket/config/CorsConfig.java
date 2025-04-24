package com.springboot.bus2tangticket.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")               // cho tất cả các đường dẫn
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*")             // GET, POST, PUT, DELETE, …
                .allowedHeaders("*")             // cho phép tất cả headers
                .allowCredentials(true);         // nếu cần cookies / auth
    }
}