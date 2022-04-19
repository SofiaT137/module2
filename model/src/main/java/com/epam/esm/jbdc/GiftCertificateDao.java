package com.epam.esm.jbdc;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

import java.util.List;
import java.util.Map;

/**
 * GiftCertificateDao interface implements CRUDDao functionality and adds a special GiftCertificate functionality
 */
public interface GiftCertificateDao extends CRUDDao<GiftCertificate> {

    /**
     * The method searches the GiftCertificate entity by passing values in a 'gift_certificate' table
     * @param mapWithFilters Map(key=filter, value=passed value)
     * @return list of the GiftCertificate entities
     */
    List<GiftCertificate> getQueryWithConditions(Map<String,String> mapWithFilters) throws DaoException;

    /**
     * The method adds tags to the GiftCertificate entity in a 'gift_certificate_tag' table.
     * @param id GiftCertificate id
     * @param tagList list of tags
     */
    void addTagsToCertificate(long id, List<Tag> tagList) throws DaoException;

    /**
     * The method deletes GiftCertificate entity relations in a 'gift_certificate_tag' table
     * @param id GiftCertificate id
     */
    void deleteListOfCertificateTags(long id) throws DaoException;
}
