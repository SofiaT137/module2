package com.epam.esm.repository;

import com.epam.esm.entity.User;

import java.util.Optional;

/**
 * UserRepository interface contains the UserCustomRepo functionality for the User entity
 */
public interface UserCustomRepo {

    /**
     * Method blockUser helps to block the User entity
     * @param entity User entity
     * @return Optional of the User entity
     */
    Optional<User> blockUser(User entity);

    /**
     * Method blockUser helps to unblock the User entity
     * @param entity User entity
     * @return Optional of the User entity
     */
    Optional<User> unblockUser(User entity);
}
