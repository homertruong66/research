package com.hoang.srj;

import com.hoang.srj.dao.GenericDaoImpl;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class Application {

    private Logger logger = Logger.getLogger(Application.class);

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                logger.info("Configure cross-origin...");
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    public static void main (String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
