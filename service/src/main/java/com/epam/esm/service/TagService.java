package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.CRDService;

import java.util.List;

/**
 * TagService interface features CRD Service functionality and extends CRDService
 */
public interface TagService<T> extends CRDService<T> {

    T findTheMostWidelyUsedUserTagWithHigherOrderCost(Long userId);

    T findTagByTagName(String name);

    List<Tag> getCertificateTagList(List<Tag> tagList);
}
