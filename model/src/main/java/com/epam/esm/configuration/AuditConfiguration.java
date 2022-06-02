package com.epam.esm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Class AuditConfiguration is a configuration class that allows setting up the JPA verification configuration
 */
@Configuration
@EnableJpaAuditing
public class AuditConfiguration {

    /**
     * Method auditorAware helps to activating auditing via Java configuration
     * @return AuditorAware entity
     */
    @Bean
    public AuditorAware<String> auditorAware(){
        return () -> Optional.of(getCurrentUser());
    }

    /**
     * Method getCurrentUser assists in getting the current user from the SecurityContextHolder
     * @return String login
     */
    public String getCurrentUser(){
        try{
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }catch (NullPointerException exception){
            return "new User";
        }
    }

}
