package com.epam.esm.service;


import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserService interface features User Service functionality and implements RService functionality
 * @param <T> The entity object
 */
public interface UserService<T> extends RService<T>{
    UserDetails loadUserByUsername(String username);
}
