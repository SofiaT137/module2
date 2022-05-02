package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


/**
 * Class TagDaoImpl is implementation of interface TagDao
 * This class is intended for work with 'tag' and 'gift_certificate_tag' tables
 */
@Repository
public class TagDaoImpl implements TagDao  {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String ID_COLUMN = "id";
    private static final String GET_MOST_POPULAR_USER_TAG_QUERY = "SELECT m.id, m.tag_name FROM (SELECT s.id,s.tag_name,SUM(s.price) as summa "+
            "FROM (SELECT t.id,t.tag_name,ord.price FROM tags AS t "+
            "INNER JOIN gift_certificate_tag AS gst ON gst.tag_id=t.id "+
            "INNER JOIN gift_certificates AS gs ON gst.gift_certificate_id=gs.id "+
            "INNER JOIN order_certificate AS ord_cer ON gs.id=ord_cer.gift_certificate_id "+
            "INNER JOIN orders AS ord ON ord_cer.order_id=ord.id WHERE ord.user_id = :userId group by order_id,tag_name) "+
            "AS s group by s.tag_name order by summa desc) as m limit 1";

    @Override
    @Transactional
    public Optional<Tag> insert(Tag entity) {
        entityManager.persist(entity);
        return Optional.of(entity);
    }

    @Override
    @Transactional
    public void deleteByID(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        entityManager.remove(tag);
    }

    @Override
    public Optional<Tag> getById(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        return tag != null ? Optional.of(tag) : Optional.empty();
    }


    @Override
    public List<Tag> getAll(int pageSize, int pageNumber){
       Query query = entityManager.createQuery("from Tag order by id");
       query.setFirstResult((pageNumber-1) * pageSize);
       query.setMaxResults(pageSize);
       return query.getResultList();
    }

    @Override
    public Optional<Tag> findTheMostWidelyUsedUserTagWithHighersOrderCost(Long userId) {
        User foundUser = entityManager.find(User.class,userId);
        Query query = entityManager.createNativeQuery(GET_MOST_POPULAR_USER_TAG_QUERY,Tag.class);
        query.setParameter("userId",foundUser.getId());
        Tag foundTag = (Tag) query.getSingleResult();
        return foundTag != null? Optional.of(foundTag) : Optional.empty();
    }
}