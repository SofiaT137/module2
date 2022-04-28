package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;
import java.util.List;

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
            List<Tag> list = session.createQuery("select t from Tag t",Tag.class).getResultList();
            session.getTransaction().commit();
        }
        finally{
                session.close();
                factory.close();
            }
    }
}
