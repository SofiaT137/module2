package com.epam.esm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware(){
        return () -> Optional.of(getCurrentUser());
    }

    public String getCurrentUser(){
        try{
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }catch (NullPointerException exception){
            return "new User";
        }
    }

}
