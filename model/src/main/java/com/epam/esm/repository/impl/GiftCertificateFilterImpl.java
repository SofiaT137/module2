package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.GiftCertificateFilter;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class GiftCertificateDaoImpl is implementation of interface GiftCertificateRepository
 * This class is intended for work with GiftCertificate entity
 */

class GiftCertificateFilterImpl implements GiftCertificateFilter {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String NAME = "name";
    private static final String CREATE_DATE = "createdDate";
    private static final String TAG_PARAMETER = "tagName";
    private static final String PERCENT = "%";
    private static final String JOIN_TAGS = "tagList";
    private static final String PART_OF_CERTIFICATE_NAME = "partName";
    private static final String PART_OF_CERTIFICATE_DESCRIPTION = "partDescription";
    private static final String GIFT_CERTIFICATE_NAME = "name";
    private static final String SORT_BY_NAME = "sortByName";
    private static final String SORT_BY_DATE = "sortByCreationDate";
    private static final String DESCRIPTION = "description";
    private static final String WHERE = " WHERE ";
    private static final String DESC = "desc";

    @Override
    public Page<GiftCertificate> findGiftCertificatesByTransferredConditions(MultiValueMap<String,
            String> mapWithFilters, int pageNumber, int pageSize){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        List<Predicate> predicates = new ArrayList<>();
        getMainQuery(mapWithFilters,predicates,criteriaBuilder,root);
        criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        criteriaQuery = getSorting(mapWithFilters,criteriaQuery,criteriaBuilder,root);
        List<GiftCertificate> giftCertificates = entityManager.createQuery(criteriaQuery.select(root)).getResultList();
        return getRequiredPage(giftCertificates,pageNumber,pageSize);
    }

    private void getMainQuery(MultiValueMap<String, String> mapWithFilters,List<Predicate> predicates,
                              CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root){
        if(mapWithFilters.get(TAG_PARAMETER)!=null){
            addJoinByTagsPart(mapWithFilters,predicates,criteriaBuilder,root);
        }
        if( mapWithFilters.get(PART_OF_CERTIFICATE_NAME)!=null){
            addSearchByCertificateName(mapWithFilters,predicates,criteriaBuilder,root);
        }
        if( mapWithFilters.get(PART_OF_CERTIFICATE_DESCRIPTION)!=null){
            addSearchByCertificateDescription(mapWithFilters,predicates,criteriaBuilder,root);
        }
    }

    private CriteriaQuery<GiftCertificate> getSorting(MultiValueMap<String, String> mapWithFilters,
                                                       CriteriaQuery<GiftCertificate> giftCertificateCriteriaQuery,
                                                       CriteriaBuilder criteriaBuilder,
                                                       Root<GiftCertificate> root){
        List<String> filter;
        if(mapWithFilters.get(SORT_BY_NAME) != null){
             filter = mapWithFilters.get(SORT_BY_NAME);
            giftCertificateCriteriaQuery = addSorting(giftCertificateCriteriaQuery,criteriaBuilder,
                    root,filter,NAME);
        }
        if(mapWithFilters.get(SORT_BY_DATE) != null) {
            filter = mapWithFilters.get(SORT_BY_DATE);
            giftCertificateCriteriaQuery = addSorting(giftCertificateCriteriaQuery,criteriaBuilder,
                    root,filter,CREATE_DATE);
        }
        return giftCertificateCriteriaQuery;
    }

    private void addJoinByTagsPart(MultiValueMap<String, String> mapWithFilters,List<Predicate> predicates,
                                   CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root){
        List<String> tags = mapWithFilters.get(TAG_PARAMETER);
        for (String tag:tags){
            Join<GiftCertificate,Tag> joinTags = root.join(JOIN_TAGS);
            predicates.add(
                    criteriaBuilder.equal(joinTags.get(NAME),tag));
        }
    }
    private void addSearchByCertificateName(MultiValueMap<String, String> mapWithFilters, List<Predicate> predicates,
                                            CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root){

        String name = mapWithFilters.get(PART_OF_CERTIFICATE_NAME).get(0);
        predicates.add(
                criteriaBuilder.like(root.get(GIFT_CERTIFICATE_NAME),PERCENT + name + PERCENT));
    }

    private void addSearchByCertificateDescription(MultiValueMap<String, String> mapWithFilters,
                                                   List<Predicate> predicates,
                                                   CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root){

        String description = mapWithFilters.get(PART_OF_CERTIFICATE_DESCRIPTION).get(0);
        predicates.add(
                criteriaBuilder.like(root.get(DESCRIPTION),PERCENT + description + PERCENT));
    }


    private CriteriaQuery<GiftCertificate> addSorting(CriteriaQuery<GiftCertificate> criteriaQuery,
                                             CriteriaBuilder criteriaBuilder,
                                             Root<GiftCertificate> root,
                                             List<String> filter,
                                             String order){
        if (filter.get(0).equalsIgnoreCase(DESC)){
            return criteriaQuery.orderBy(criteriaBuilder.desc(root.get(order)));
        }else {
            return criteriaQuery.orderBy(criteriaBuilder.asc(root.get(order)));
        }
    }

    @Override
    public Optional<GiftCertificate> update(int duration, GiftCertificate entity) {
        GiftCertificate mergedCertificate = entityManager.merge(entity);
        entity.setDuration(duration);
        return mergedCertificate != null ? Optional.of(mergedCertificate) : Optional.empty();
    }

    private Page<GiftCertificate> getRequiredPage(List<GiftCertificate> giftCertificates,int pageNumber,int pageSize){
        PagedListHolder<GiftCertificate> pagedListHolder = new PagedListHolder<>();
        pagedListHolder.setSource(giftCertificates);
        pagedListHolder.setPage(pageNumber);
        pagedListHolder.setPageSize(pageSize);
        return new PageImpl<>(pagedListHolder.getPageList(),PageRequest.of(pageNumber,pageSize),
                giftCertificates.size());
    }
}

