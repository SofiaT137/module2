package com.epam.esm.service;


import com.epam.esm.entity.User;


/**
 * UserService interface features User Service functionality and implements CRService functionality
 * @param <T> The entity object
 */
public interface UserService<T> extends CRService<T>{

    /**
     * The method provides service layer functionality for searching the User entity by its login
     * @param login String user login
     * @return User user
     */
    User findUserByUserLogin(String login);

    /**
     * The method provides service layer functionality for blocking the User entity by its login
     * @param login String user login
     * @return User user
     */
    User blockUser(String login);

    /**
     * The method provides service layer functionality for unblocking the User entity by its login
     * @param login String user login
     * @return User user
     */
    User unblockUser(String login);
}

