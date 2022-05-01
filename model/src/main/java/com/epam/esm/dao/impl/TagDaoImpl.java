package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    private static final String GET_MOST_POPULAR_USER_TAG_QUERY = "SELECT m.tag_id,m.tag_name FROM (SELECT s.tag_name,SUM(s.price) as summa "+
            "FROM (SELECT t.tag_name,ord.price FROM tags AS t "+
            "INNER JOIN gift_certificate_tag AS gst ON gst.tag_id=t.id "+
            "INNER JOIN gift_certificates AS gs ON gst.gift_certificate_id=gs.id "+
            "INNER JOIN order_certificate AS ord_cer ON gs.id=ord_cer.gift_certificate_id "+
            "INNER JOIN orders AS ord ON ord_cer.order_id=ord.id WHERE ord.user_id = :id_user group by order_id,tag_name) " +
            "AS s group by s.tag_name order by summa desc) as m limit 1";

    @Override
    public Optional<Tag> insert(Tag entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return Optional.of(entity);
    }

    @Override
    public void deleteByID(long id) {
        entityManager.getTransaction().begin();
        Tag tag = entityManager.find(Tag.class, id);
        entityManager.remove(tag);
        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<Tag> getById(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        return tag != null ? Optional.of(tag) : Optional.empty();
    }


    @Override
    public List<Tag> getAll() {
        return entityManager.createQuery("from Tag").getResultList();
    }

    @Override
    public Optional<Tag> findTheMostWidelyUsedUserTagWithHighersOrderCost(User user) {
        User foundUser = entityManager.find(User.class,user.getId());
        Query query = entityManager.createNativeQuery(GET_MOST_POPULAR_USER_TAG_QUERY,Tag.class);
        query.setParameter("id_user",foundUser.getId());
        Tag foundTag = (Tag) query.getSingleResult();
        return foundTag != null? Optional.of(foundTag) : Optional.empty();
    }
}
