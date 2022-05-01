package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;


public class Test {

//    private static final String GET_MOST_POPULAR_USER_TAG_QUERY = "SELECT tags.id,tags.tag_name FROM tags as tags RIGHT JOIN (SELECT t.id,tag_name,count(tag_name) as count_tag FROM users AS us "+
//            "INNER JOIN orders AS ord ON ord.user_id=us.id "+
//            "INNER JOIN order_certificate as ord_cer ON ord.id=ord_cer.order_id "+
//            "INNER JOIN gift_certificates as gif_cer ON gif_cer.id=ord_cer.gift_certificate_id "+
//            "INNER JOIN gift_certificate_tag as gif_cer_tag ON gif_cer_tag.gift_certificate_id=gif_cer.id "+
//            "INNER JOIN tags as t ON t.id=gif_cer_tag.tag_id WHERE user_id=:id_f group by gif_cer_tag.tag_id order by count_tag desc limit 1) as m "+
//            " ON m.id = tags.id";

    private static final String GET_MOST_POPULAR_USER_TAG_QUERY = "SELECT m.id, m.tag_name FROM (SELECT s.id,s.tag_name,SUM(s.price) as summa "+
            "FROM (SELECT t.id,t.tag_name,ord.price FROM tags AS t "+
            "INNER JOIN gift_certificate_tag AS gst ON gst.tag_id=t.id "+
            "INNER JOIN gift_certificates AS gs ON gst.gift_certificate_id=gs.id "+
            "INNER JOIN order_certificate AS ord_cer ON gs.id=ord_cer.gift_certificate_id "+
            "INNER JOIN orders AS ord ON ord_cer.order_id=ord.id WHERE ord.user_id = :id_f group by order_id,tag_name) "+
            "AS s group by s.tag_name order by summa desc) as m limit 1";

    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(GiftCertificate.class)
                .addAnnotatedClass(Tag.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        Session session = null;

        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            User user = session.get(User.class,16L);
            Query query = session.createNativeQuery(GET_MOST_POPULAR_USER_TAG_QUERY,Tag.class);
            query.setParameter("id_f",user.getId());
            Tag theMostPopularTag = (Tag) query.getSingleResult();
            System.out.println(theMostPopularTag);
            session.getTransaction().commit();
        }
        finally{
                session.close();
                factory.close();
            }
    }
}
