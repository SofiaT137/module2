package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.CRDService;

import java.util.List;

/**
 * TagService interface features Tag Service functionality and extends CRDService functionality
 * @param <T> The entity object
 */
public interface TagService<T> extends CRDService<T> {

    /**
     * The method provides service layer functionality for searching the Tag entity by its identifier
     * @param userId User id (Long value)
     * @return Tag or TagDto entity
     */
    T findTheMostWidelyUsedUserTagWithHigherOrderCost(Long userId);

    /**
     * The method provides service layer functionality for searching the Tag entity by its name
     * @param name String name of the tag
     * @return Tag or TagDto entity
     */
    T findTagByTagName(String name);

    /**
     * The method provides a service layer functionality for searching all tag entities
     * that have been transferred into the method
     * @param tagList List of Tags
     * @return List of Tags
     */
    List<Tag> getCertificateTagList(List<Tag> tagList);
}
