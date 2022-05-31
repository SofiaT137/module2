package com.epam.esm.configuration;

import com.epam.esm.security.JwtConfiguration;
import com.epam.esm.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;

@EnableMethodSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    public static final String USER = "USER";
    public static final String ADMINISTRATOR = "ADMINISTRATOR";
    public static final String CERTIFICATES_ENDPOINT = "/certificates/**";
    public static final String ORDERS_ENDPOINT = "/orders/**";
    public static final String USERS_ENDPOINT = "/users/**";
    public static final String TAGS_ENDPOINT = "/tags/**";
    public static final String ENTRANCE_ENDPOINT = "/auth/signIn";
    public static final String REGISTRATION_ENDPOINT = "/auth/signUp";

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

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
                .antMatchers(POST,ENTRANCE_ENDPOINT,REGISTRATION_ENDPOINT).permitAll()
                .antMatchers(GET,CERTIFICATES_ENDPOINT).permitAll()
                .antMatchers(GET,USERS_ENDPOINT,TAGS_ENDPOINT,ORDERS_ENDPOINT).hasAnyRole(USER,ADMINISTRATOR)
                .antMatchers(POST,ORDERS_ENDPOINT).hasAnyRole(USER,ADMINISTRATOR)
                .antMatchers(POST,TAGS_ENDPOINT,CERTIFICATES_ENDPOINT).hasRole(ADMINISTRATOR)
                .antMatchers(DELETE,USERS_ENDPOINT,TAGS_ENDPOINT,ORDERS_ENDPOINT,CERTIFICATES_ENDPOINT).
                            hasRole(ADMINISTRATOR)
                .antMatchers(PATCH,CERTIFICATES_ENDPOINT).hasRole(ADMINISTRATOR)
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfiguration(jwtTokenProvider));
    }
}
