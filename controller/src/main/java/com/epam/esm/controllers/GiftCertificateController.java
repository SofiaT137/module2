package com.epam.esm.controllers;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GiftCertificateController class presents REST controller for GiftCertificate entity
 */
@RestController
@RequestMapping("/gift_certificates")
public class GiftCertificateController {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String TAG_NAME = "tag_name";
    private static final String PERCENT = "%";
    private static final String JOIN_TAGS = "tags";
    private static final String NAME_COLUMN = "name";
    private static final String TAG_PARAMETER = "tag_name";
    private static final String PART_OF_CERTIFICATE_NAME = "partName";
    private static final String PART_OF_CERTIFICATE_DESCRIPTION = "partDescription";
    private static final String GIFT_CERTIFICATE_NAME = "giftCertificateName";
    private static final String SORT_BY_NAME = "sortByName";
    private static final String SORT_BY_DATE = "sortByCreationDate";
    private static final String DESCRIPTION = "description";

    @GetMapping("/filter")
    public List<GiftCertificate> findTheMostWidelyUsedUserTagWithHighersOrderCost(@RequestParam MultiValueMap<String, String> mapWithFilters) {
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
