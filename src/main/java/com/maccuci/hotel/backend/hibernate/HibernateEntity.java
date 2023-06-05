package com.maccuci.hotel.backend.hibernate;

import org.hibernate.HibernateException;

import javax.persistence.*;

public class HibernateEntity {
    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("HotelReservation");
    private EntityManager entityManager;

    public HibernateEntity() {
        entityManager = FACTORY.createEntityManager();
    }

//    public void instance() {
//        try {
//            entityManager = FACTORY.createEntityManager();
//        } catch (HibernateException exception) {
//            exception.printStackTrace();
//        }
//    }

    public EntityTransaction getTransaction() {
        return getEntityManager().getTransaction();
    }

    public void close() {
        getEntityManager().close();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
