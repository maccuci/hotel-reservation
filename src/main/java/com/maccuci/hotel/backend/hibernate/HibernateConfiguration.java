package com.maccuci.hotel.backend.hibernate;

import org.hibernate.HibernateException;

import javax.persistence.*;

public class HibernateConfiguration {
    private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("HotelReservation");
    private EntityManager entityManager;

    public void instance() {
        try {
            entityManager = FACTORY.createEntityManager();
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
    }

    public EntityTransaction getTransaction() {
        return getEntityManager().getTransaction();
    }

    private EntityManager getEntityManager() {
        return entityManager;
    }
}
