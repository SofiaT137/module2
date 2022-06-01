package com.epam.esm.service;


import com.epam.esm.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * UserService interface features User Service functionality and implements RService functionality
 * @param <T> The entity object
 */
public interface UserService<T> extends CRService<T>{
    UserDetails loadUserByUsername(String userName);
    User findUserByUserLogin(String login);
    User blockUser(String login);
    User unblockUser(String login);
}
