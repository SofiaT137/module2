package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.Optional;

/**
 * UserDao interface implements RDao functionality
 */
public interface UserDao extends CRDDao<User> {

    Optional<User> findByUserLogin(String login);

}
