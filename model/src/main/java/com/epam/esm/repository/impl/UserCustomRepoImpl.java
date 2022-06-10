package com.epam.esm.repository.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repository.UserCustomRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public class UserCustomRepoImpl implements UserCustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> blockUser(User entity) {
        User mergedUser = entityManager.merge(entity);
        entity.setEnabled(0);
        return mergedUser != null ? Optional.of(mergedUser) : Optional.empty();
    }

    @Override
    public Optional<User> unblockUser(User entity) {
        User mergedUser = entityManager.merge(entity);
        entity.setEnabled(1);
        return mergedUser != null ? Optional.of(mergedUser) : Optional.empty();
    }
}
