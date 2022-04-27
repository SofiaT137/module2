package com.epam.esm;

import com.epam.esm.entity.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Test {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Tag.class)
                .buildSessionFactory();
       try {
           Session session = factory.getCurrentSession();
           Tag tag = new Tag("lector");
           session.beginTransaction();
           session.save(tag);
           session.getTransaction().commit();
       }finally {
           factory.close();
       }
    }
}
