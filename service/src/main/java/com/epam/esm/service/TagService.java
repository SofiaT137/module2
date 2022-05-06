package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.CRDService;

/**
 * TagService interface features CRD Service functionality and extends CRDService
 */
public interface TagService<T> extends CRDService<T> {

    T findTheMostWidelyUsedUserTagWithHigherOrderCost(Long userId);

    T findTagByTagName(String name);
}
