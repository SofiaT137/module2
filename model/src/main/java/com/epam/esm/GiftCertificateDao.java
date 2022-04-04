package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao extends CRUDDao<GiftCertificate> {

    List<Tag> getListOfTags(long id);
    List<GiftCertificate> getByFilters(Map<String, String> filters);
    void addListOfTags(long id, List<Tag> tagList);
    void deleteListOfTags(long id, List<Tag> tagList);
}
