package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * Class UserDaoImpl is implementation of interface UserDao
 * This class is intended for work with User entity
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static final String GET_ALL_PART_QUERY = "from User order by id";

    private static final String FIND_USER_BY_NAME_QUERY = "SELECT * FROM users AS u WHERE user_name = :userName";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> getById(long id) {
        User user = entityManager.find(User.class, id);
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public List<User> getAll(int pageSize,int pageNumber) {
        Query query = entityManager.createQuery(GET_ALL_PART_QUERY);
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public Optional<User> findByUserLogin(String login) {
        Query query = entityManager.createNativeQuery(FIND_USER_BY_NAME_QUERY, User.class);
        query.setParameter("userName",login);
        User user;
        try{
            user = (User) query.getSingleResult();
        }catch (NoResultException exception){
            return Optional.empty();
        }
        return Optional.of(user);
    }
}
