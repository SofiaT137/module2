package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
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
public class TagDaoImpl implements TagDao {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String ID_COLUMN = "id";
    private static final String GET_MOST_POPULAR_USER_TAG_QUERY = "SELECT tags.id,tags.tag_name FROM tags as tags RIGHT JOIN (SELECT t.id,tag_name,count(tag_name) as count_tag FROM users AS us "+
            "INNER JOIN orders AS ord ON ord.user_id=us.id "+
            "INNER JOIN order_certificate as ord_cer ON ord.id=ord_cer.order_id "+
            "INNER JOIN gift_certificates as gif_cer ON gif_cer.id=ord_cer.gift_certificate_id "+
            "INNER JOIN gift_certificate_tag as gif_cer_tag ON gif_cer_tag.gift_certificate_id=gif_cer.id "+
            "INNER JOIN tags as t ON t.id=gif_cer_tag.tag_id WHERE user_id=:id_f group by gif_cer_tag.tag_id order by count_tag desc limit 1) as m "+
            " ON m.id = tags.id";

    @Override
    public Optional<Tag> insert(Tag tag) {
        entityManager.getTransaction().begin();
        entityManager.persist(tag);
        entityManager.getTransaction().commit();
        return Optional.of(tag);
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
    public  Optional<Tag>  findTheMostWidelyUsedUserTag(User user) {
        User foundUser = entityManager.find(User.class,user.getId());
        Query query = entityManager.createNativeQuery(GET_MOST_POPULAR_USER_TAG_QUERY,Tag.class);
        query.setParameter("id_f",foundUser.getId());
        Tag foundTag = (Tag) query.getSingleResult();
        return foundTag != null? Optional.of(foundTag) : Optional.empty();
    }
}
