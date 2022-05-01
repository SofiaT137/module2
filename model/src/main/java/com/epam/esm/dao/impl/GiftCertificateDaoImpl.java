package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class GiftCertificateDaoImpl is implementation of interface GiftCertificateDao
 * This class is intended for work with 'gift certificate' and 'gift_certificate_tag' tables
 */
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String TAG_NAME = "tag_name";
    private static final String NAME_COLUMN = "name";
    private static final String TAG_PARAMETER = "tag_name";
    private static final String PERCENT = "%";
    private static final String JOIN_TAGS = "tags";
    private static final String PART_OF_CERTIFICATE_NAME = "partName";
    private static final String PART_OF_CERTIFICATE_DESCRIPTION = "partDescription";
    private static final String GIFT_CERTIFICATE_NAME = "giftCertificateName";
    private static final String SORT_BY_NAME = "sortByName";
    private static final String SORT_BY_DATE = "sortByCreationDate";
    private static final String DESCRIPTION = "description";
    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";
    private static final String EQUALS_QUOTE = "='";
    private static final String EQUALS = "=";
     private static final String QUOTE = "'";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String DESC = " DESC";
    private static final String COMA = ", ";


    @Override
    public Optional<GiftCertificate> insert(GiftCertificate entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return Optional.of(entity);
    }

    @Override
    public void deleteByID(long id) {
        entityManager.getTransaction().begin();
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        entityManager.remove(giftCertificate);
        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificate entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return Optional.of(entity);
    }

    @Override
    public Optional<GiftCertificate> getById(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        return giftCertificate != null ? Optional.of(giftCertificate) : Optional.empty();
    }

    @Override
    public List<GiftCertificate> getAll() {
        return entityManager.createQuery("from GiftCertificate").getResultList();
    }

    @Override
    public List<GiftCertificate> findGiftCertificatesByTransferredConditions(@RequestParam MultiValueMap<String, String> mapWithFilters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        List<Predicate> predicates = new ArrayList<>();

        if(mapWithFilters.get(TAG_PARAMETER)!=null){
            List<String> tags = mapWithFilters.get(TAG_PARAMETER);
            for (String tag:tags){
                Join<GiftCertificate,Tag> joinTags = root.join(JOIN_TAGS);
                predicates.add(
                        criteriaBuilder.equal(joinTags.get(NAME_COLUMN),tag));
            }
        }
        if( mapWithFilters.get(PART_OF_CERTIFICATE_NAME)!=null){
            String name = mapWithFilters.get(PART_OF_CERTIFICATE_NAME).get(0);
            predicates.add(
                    criteriaBuilder.like(root.get(GIFT_CERTIFICATE_NAME),PERCENT+ name +PERCENT));
        }

        if( mapWithFilters.get(PART_OF_CERTIFICATE_DESCRIPTION)!=null){
            String description = mapWithFilters.get(PART_OF_CERTIFICATE_DESCRIPTION).get(0);
            predicates.add(
                    criteriaBuilder.like(root.get(DESCRIPTION),PERCENT+ description +PERCENT));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        CriteriaQuery<GiftCertificate> select = criteriaQuery.select(root);

        List<GiftCertificate> resultList = entityManager.createQuery(select).getResultList();

        if(mapWithFilters.get(SORT_BY_NAME) != null){
            if(mapWithFilters.get(SORT_BY_NAME).get(0).equalsIgnoreCase("asc")){
                return resultList.stream()
                        .sorted(Comparator.comparing(GiftCertificate::getGiftCertificateName)).collect(Collectors.toList());
            }else{
                return resultList.stream()
                        .sorted(Comparator.comparing(GiftCertificate::getGiftCertificateName).reversed()).collect(Collectors.toList());
            }
        }

        if(mapWithFilters.get(SORT_BY_DATE) != null){
            if(mapWithFilters.get(SORT_BY_DATE).get(0).equalsIgnoreCase("asc")){
                return resultList.stream()
                        .sorted(Comparator.comparing(GiftCertificate::getCreateDate)).collect(Collectors.toList());
            }else{
                return resultList.stream()
                        .sorted(Comparator.comparing(GiftCertificate::getCreateDate).reversed()).collect(Collectors.toList());
            }
        }
        return resultList;
    }
}

