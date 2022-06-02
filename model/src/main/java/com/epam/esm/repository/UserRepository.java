package com.epam.esm.repository;

import com.epam.esm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserRepository interface implements JpaRepository and UserCustomRepo functionality for the User entity
 */
public interface UserRepository extends JpaRepository<User, Long>,UserCustomRepo{

    /**
     * Method findUserByLogin searches the User entity by its login
     * @param login String login
     * @return Optional of the User entity
     */
    Optional<User> findUserByLogin(String login);
}
