package com.epam.esm.jbdc;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

import java.util.List;

/**
 * TagDao interface extends CRDDao functionality and adds a special Tag DAO functionality
 */
public interface TagDao extends CRDDao<Tag> {

    /**
     * The method finds the Tag entity in a destination table by its name
     * @param name String tag name
     * @return The Tag entity
     */
    Tag getTagByName(String name);

    /**
     * The method returns a list of all the identifiers in the list of past Tag entities.
     * If one of the past tags does not exist, method creates a new one and puts its id in the list.
     * @param tagList Tag list
     * @return List of Tags id
     */
    List<Long> getListWithTagsId(List<Tag> tagList);
}
