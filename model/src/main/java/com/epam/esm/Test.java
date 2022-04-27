package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;

public class Test {
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
            Order order = new Order(78.19, LocalDateTime.now());
            session.beginTransaction();
            GiftCertificate giftCertificate1 = session.get(GiftCertificate.class, 1L);
            Tag tag1 = session.get(Tag.class, 1L);
            Tag tag2 = session.get(Tag.class, 2L);
            Tag tag3 = session.get(Tag.class, 3L);
            Tag tag4 = session.get(Tag.class, 4L);
            giftCertificate1.addTagToGiftCertificate(tag1);
            giftCertificate1.addTagToGiftCertificate(tag2);
            giftCertificate1.addTagToGiftCertificate(tag3);
            giftCertificate1.addTagToGiftCertificate(tag4);
            session.save(giftCertificate1);
            session.getTransaction().commit();
        }
        finally{
                session.close();
                factory.close();
            }
    }
}
