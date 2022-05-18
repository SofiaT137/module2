package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * TagDao interface extends CRDDao functionality and adds a special Tag DAO functionality
 */
public interface TagDao extends CRDDao<Tag> {

    /**
     * Method findTheMostWidelyUsedUserTagWithHighersOrderCost searches the widely used user tag with higher
     * order cost
     * @param userId User id (Long value)
     * @return Optional of Tag
     */
    List<Tag> findTheMostWidelyUsedUserTagWithHighersOrderCost(Long userId);

    /**
     * Method findTagByTagName searches tag by its name
     * @param name String tag name
     * @return Optional of Tag
     */
    Optional<Tag> findTagByTagName(String name);
}
