package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * SpringBootApplicationRunner is a main configuration class helps to run SpringBoot Application
 */
@EnableWebMvc
@SpringBootApplication
public class SpringBootApplicationRunner extends SpringBootServletInitializer {

    private static final String DISPATCHER_SERVLET = "dispatcherServlet";

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootApplicationRunner.class, args);
        DispatcherServlet dispatcherServlet = (DispatcherServlet) context.getBean(DISPATCHER_SERVLET);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}
