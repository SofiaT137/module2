package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.CRDService;

/**
 * TagService interface features CRD Service functionality and extends CRDService
 */
public interface TagService<T> extends CRDService<T> {
    /**
     * The method provides service layer logic for searching Tag entity by passing values
     * @param name String tag name
     * @return The Tag entity
     */
    T getTagByName(String name);
}
