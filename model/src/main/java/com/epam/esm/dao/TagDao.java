package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;

import java.util.Optional;

/**
 * TagDao interface extends CRDDao functionality and adds a special Tag DAO functionality
 */
public interface TagDao extends CRDDao<Tag> {

    Optional<Tag> findTheMostWidelyUsedUserTagWithHighersOrderCost(User user);
}
