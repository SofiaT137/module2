package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Order> insert(Order entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return Optional.of(entity);
    }

    @Override
    public void deleteByID(long id) {
        entityManager.getTransaction().begin();
        Order order = entityManager.find(Order.class, id);
        entityManager.remove(order);
        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<Order> getById(long id) {
       Order order = entityManager.find(Order.class, id);
        return order != null ? Optional.of(order) : Optional.empty();
    }

    @Override
    public List<Order> getAll(int pageSize,int pageNumber) {
        Query query = entityManager.createQuery("from Order order by id");
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
}
