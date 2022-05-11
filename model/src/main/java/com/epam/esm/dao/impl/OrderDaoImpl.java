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

    private static final String FIND_ORDERS_BY_USER_ID_QUERY = "SELECT * FROM orders WHERE user_id = :userId";
    private static final String USER_ID = "userId";

    @Override
    public Optional<Order> insert(Order entity) {
        entityManager.persist(entity);
        return Optional.of(entity);
    }

    @Override
    public void deleteByID(long id) {
        Order order = entityManager.find(Order.class, id);
        entityManager.remove(order);
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


    @Override
    public List<Order> ordersByUserId(long userId, int pageSize, int pageNumber) {
        Query query = entityManager.createNativeQuery(FIND_ORDERS_BY_USER_ID_QUERY, Order.class);
        query.setParameter(USER_ID,userId);
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
}
