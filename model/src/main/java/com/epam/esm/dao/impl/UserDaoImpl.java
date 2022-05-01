package com.epam.esm.dao.impl;

import com.epam.esm.dao.RDao;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements RDao<User> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> getById(long id) {
        User user = entityManager.find(User.class, id);
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public List<User> getAll() {
       return entityManager.createQuery("from User").getResultList();
    }
}
