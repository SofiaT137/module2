package com.epam.esm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Class PasswordEncoderConfiguration is a configuration class of encoding logic
 */
@Configuration
public class PasswordEncoderConfiguration {

    /**
     * Method passwordEncoder returns BCryptPasswordEncoder bean
     * @return BCryptPasswordEncoder entity
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}