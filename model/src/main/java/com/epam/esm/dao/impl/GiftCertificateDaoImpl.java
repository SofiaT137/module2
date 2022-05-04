package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
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
    private static final String ASC = "asc";
    private static final String PART_OF_SEARCHING_QUERY = "from GiftCertificate order by id";


    @Override
    public Optional<GiftCertificate> insert(GiftCertificate entity) {
        entityManager.persist(entity);
        return Optional.of(entity);
    }

    @Override
    public void deleteByID(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        entityManager.remove(giftCertificate);
    }

    @Override
    public Optional<GiftCertificate> update(int duration,GiftCertificate entity) {
        GiftCertificate mergedCertificate = entityManager.merge(entity);
        entity.setDuration(duration);
        entity.setLastUpdateDate(LocalDateTime.now());
        return mergedCertificate != null ? Optional.of(mergedCertificate) : Optional.empty();
    }

    @Override
    public Optional<GiftCertificate> getById(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        return giftCertificate != null ? Optional.of(giftCertificate) : Optional.empty();
    }

    @Override
    public List<GiftCertificate> getAll(int pageSize,int pageNumber) {
        Query query = entityManager.createQuery(PART_OF_SEARCHING_QUERY);
        query.setFirstResult((pageNumber-1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public List<GiftCertificate> findGiftCertificatesByTransferredConditions(MultiValueMap<String, String> mapWithFilters){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        List<Predicate> predicates = new ArrayList<>();

        getMainQuery(mapWithFilters,predicates,criteriaBuilder,root);

        criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        CriteriaQuery<GiftCertificate> select = criteriaQuery.select(root);

        List<GiftCertificate> resultList = entityManager.createQuery(select).getResultList();

        getSortForResultList(mapWithFilters,resultList);

        return resultList;
    }

    private void getMainQuery(MultiValueMap<String, String> mapWithFilters,List<Predicate> predicates,
                              CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root){
        if(mapWithFilters.get(TAG_PARAMETER)!=null){
            addJoinByTagsPart(mapWithFilters,predicates,criteriaBuilder,root);
        }
        if( mapWithFilters.get(PART_OF_CERTIFICATE_NAME)!=null){
            addSearchByPart(mapWithFilters,predicates,criteriaBuilder,root,PART_OF_CERTIFICATE_NAME);
        }
        if( mapWithFilters.get(PART_OF_CERTIFICATE_DESCRIPTION)!=null){
            addSearchByPart(mapWithFilters,predicates,criteriaBuilder,root,PART_OF_CERTIFICATE_DESCRIPTION);
        }
    }

    private void getSortForResultList(MultiValueMap<String, String> mapWithFilters,List<GiftCertificate> resultList){
        if(mapWithFilters.get(SORT_BY_NAME) != null){
            addSortingCollectionPart(mapWithFilters,SORT_BY_NAME,resultList);
        }
        if(mapWithFilters.get(SORT_BY_DATE) != null) {
            addSortingCollectionPart(mapWithFilters,SORT_BY_DATE,resultList);
        }
    }

    private void addJoinByTagsPart(MultiValueMap<String, String> mapWithFilters,List<Predicate> predicates,
                                   CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root){
        List<String> tags = mapWithFilters.get(TAG_PARAMETER);
        for (String tag:tags){
            Join<GiftCertificate,Tag> joinTags = root.join(JOIN_TAGS);
            predicates.add(
                    criteriaBuilder.equal(joinTags.get(NAME_COLUMN),tag));
        }
    }
    private void addSearchByPart(MultiValueMap<String, String> mapWithFilters, List<Predicate> predicates,
                                 CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root, String searchPart){
        String name = mapWithFilters.get(searchPart).get(0);
        String equalsSearchPart = searchPart.equals(PART_OF_CERTIFICATE_DESCRIPTION)? DESCRIPTION:GIFT_CERTIFICATE_NAME;
        predicates.add(
                criteriaBuilder.like(root.get(equalsSearchPart),PERCENT+ name +PERCENT));
    }

    private List<GiftCertificate> addSortingCollectionPart(MultiValueMap<String, String> mapWithFilters,
                                                           String sortBy, List<GiftCertificate> resultList){
        if(mapWithFilters.get(sortBy).get(0).equalsIgnoreCase(ASC)){
            return resultList.stream()
                    .sorted(Comparator.comparing(GiftCertificate::getGiftCertificateName))
                    .collect(Collectors.toList());
        }else{
            return resultList.stream()
                    .sorted(Comparator.comparing(GiftCertificate::getGiftCertificateName)
                            .reversed()).collect(Collectors.toList());
        }
    }
}

