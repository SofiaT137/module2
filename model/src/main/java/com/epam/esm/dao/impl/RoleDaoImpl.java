package com.epam.esm.dao.impl;

import com.epam.esm.dao.RoleDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    private static final String GET_ALL_PART_QUERY = "from Order order by id";

    @Override
    public Optional<Role> insert(Role entity) {
        entityManager.persist(entity);
        return Optional.of(entity);
    }

    @Override
    public void delete(Role entity) {
        entityManager.remove(entity);
    }

    @Override
    public Optional<Role> getById(long id) {
        Role role = entityManager.find(Role.class, id);
        return role != null ? Optional.of(role) : Optional.empty();
    }

    @Override
    public List<Role> getAll(int pageSize, int pageNumber) {
        Query query = entityManager.createQuery(GET_ALL_PART_QUERY);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
}
