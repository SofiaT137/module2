package com.epam.esm.jbdc;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

/**
 * TagDao interface extends CRDDao functionality and adds a special TagDao functionality
 */
public interface TagDao extends CRDDao<Tag> {

    /**
     * The method finds the Tag entity in a destination table by its name
     * @param name String tag name
     * @return The Tag entity
     */
    Tag getTagByName(String name) throws DaoException;
}
