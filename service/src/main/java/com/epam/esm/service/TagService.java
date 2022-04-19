package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

/**
 * TagService interface features CRD Service functionality and extends CRDService
 */
public interface TagService extends CRDService<Tag>{
    /**
     * The method provides service layer logic for searching Tag entity by passing values
     * @param name String tag name
     * @return The Tag entity
     */
    Tag getTagByName(String name) throws DaoException;
}
