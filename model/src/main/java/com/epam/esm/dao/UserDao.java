package com.epam.esm.dao;

import com.epam.esm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long>{

    @Query(value = "SELECT * FROM users AS u WHERE u.user_name = :login",
            nativeQuery = true)
    Optional<User> findByUserLogin(String login);

}
