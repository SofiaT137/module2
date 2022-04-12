package com.epam.esm.jbdc;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDao extends CRUDDao<GiftCertificate> {

    List<GiftCertificate> getQueryWithConditions(Map<String,String> mapWithFilters) throws DaoException;

    void addTagsToCertificate(long id, List<Tag> tagList) throws DaoException;

    void deleteListOfCertificateTags(long id) throws DaoException;

}
