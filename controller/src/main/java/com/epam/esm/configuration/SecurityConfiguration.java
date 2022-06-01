package com.epam.esm.configuration;

import com.epam.esm.security.JwtConfiguration;
import com.epam.esm.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;

/**
 * Security configuration class for JWT based Spring Security application
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    public static final String USER = "USER";
    public static final String ADMINISTRATOR = "ADMINISTRATOR";
    public static final String CERTIFICATES_ENDPOINT = "/gift_certificates/**";
    public static final String ORDERS_COMMON_ENDPOINT = "/orders/**";
    public static final String ORDERS_GET_ORDERS_BY_USER_ID_ADMIN_ENDPOINT = "/orders/users/**";
    public static final String ORDERS_GET_ORDERS_BY_ID_ADMIN_ENDPOINT = "/orders/{\\d+}";
    public static final String USERS_ENDPOINT = "/users/**";
    public static final String TAGS_ENDPOINT = "/tags/**";
    public static final String ENTRANCE_ENDPOINT = "/auth/signIn";
    public static final String REGISTRATION_ENDPOINT = "/auth/signUp";

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Method AuthenticationManager returns AuthenticationManager bean that defines
     * how Spring Security's Filters perform authentication.
     * @return AuthenticationManager entity
     * @throws Exception exception
     */
    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception{
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(GET,CERTIFICATES_ENDPOINT).permitAll()
                .antMatchers(POST,ENTRANCE_ENDPOINT,REGISTRATION_ENDPOINT).permitAll()
                .antMatchers(GET,TAGS_ENDPOINT,ORDERS_COMMON_ENDPOINT).hasAnyRole(USER,ADMINISTRATOR)
                .antMatchers(GET,USERS_ENDPOINT,ORDERS_GET_ORDERS_BY_USER_ID_ADMIN_ENDPOINT,
                        ORDERS_GET_ORDERS_BY_ID_ADMIN_ENDPOINT).hasRole(ADMINISTRATOR)
                .antMatchers(POST,ORDERS_COMMON_ENDPOINT).hasAnyRole(USER,ADMINISTRATOR)
                .antMatchers(POST,TAGS_ENDPOINT,CERTIFICATES_ENDPOINT).hasRole(ADMINISTRATOR)
                .antMatchers(DELETE,TAGS_ENDPOINT,ORDERS_COMMON_ENDPOINT,CERTIFICATES_ENDPOINT).
                            hasRole(ADMINISTRATOR)
                .antMatchers(PATCH,CERTIFICATES_ENDPOINT,USERS_ENDPOINT).hasRole(ADMINISTRATOR)
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfiguration(jwtTokenProvider));
    }
}
