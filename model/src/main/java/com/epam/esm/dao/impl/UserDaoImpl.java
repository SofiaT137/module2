package com.epam.esm.dao.impl;

import com.epam.esm.dao.RDao;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    public List<User> getAll(int pageSize,int pageNumber) {
        Query query = entityManager.createQuery("from User order by id");
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
}
