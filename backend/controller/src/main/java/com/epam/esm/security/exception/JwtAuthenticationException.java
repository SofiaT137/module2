package com.epam.esm.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Class JwtAuthenticationException helps to create the JwtAuthenticationException entity
 */
public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
