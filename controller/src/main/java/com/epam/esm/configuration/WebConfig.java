package com.epam.esm.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc // используется для включения Spring MVC в приложении и работает путем импорта конфигурации Spring MVC из WebMvcConfigurer
@Configuration
@ComponentScan("com.epam.esm")
public class WebConfig implements WebMvcConfigurer {

    ApplicationContext applicationContext;//это главный интерфейс в Spring-приложении, который предоставляет информацию о конфигурации приложения.

    @Autowired
    public WebConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
