package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GiftCertificateDao extends CRUDDao<GiftCertificate> {

    void addTagsToCertificate(long id, Set<Tag> tagList) throws DaoException;
    void deleteListOfCertificateTags(long id) throws DaoException;
}
