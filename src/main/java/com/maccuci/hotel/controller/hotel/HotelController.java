package com.maccuci.hotel.controller.hotel;

import com.maccuci.hotel.Main;
import com.maccuci.hotel.backend.hibernate.HibernateEntity;
import com.maccuci.hotel.backend.redis.RedisDatabase;
import com.maccuci.hotel.manager.logger.MLogger;

import javax.persistence.TypedQuery;
import java.util.List;

public class HotelController {

    private final MLogger logger = new MLogger("HC");
    private final HibernateEntity hibernateEntity = new HibernateEntity();
    private final RedisDatabase redisDatabase = Main.getRedisDatabaseController();

    public void create(String hotelName, String location, boolean reserved, int persons) {
        Hotel hotel = new Hotel(hotelName, location, reserved, persons);

        try {
            hibernateEntity.getTransaction().begin();
            hibernateEntity.getEntityManager().persist(hotel);
            hibernateEntity.getTransaction().commit();
            redisDatabase.keyAndValue("hotel:" + hotel.getId(), hotelName);
            logger.log(MLogger.Level.SUCCESS, "Hotel was created!");
        } catch (Exception exception) {
            logger.log(MLogger.Level.ERROR, exception.getCause(), "Error when create a Hotel.");
            exception.printStackTrace();
        }
    }

    public Hotel find(Object o) {
        return hibernateEntity.getEntityManager().find(Hotel.class, o);
    }

    public void delete(int id) {
        Hotel hotel = find(id);

        if (hotel == null) return;

        hibernateEntity.getTransaction().begin();
        try {
            hibernateEntity.getEntityManager().remove(hotel);
            hibernateEntity.getTransaction().commit();
            System.out.println("The hotel was deleted!");
        } catch (Exception e) {
            System.out.printf("Error when deleted the hotel: %s", e.getMessage());
            hibernateEntity.getTransaction().rollback();
        } finally {
            hibernateEntity.close();
        }
    }

    public void list() {
        try {
            TypedQuery<Hotel> query = hibernateEntity.getEntityManager().createQuery("from hotel", Hotel.class);
            List<Hotel> accounts = query.getResultList();

            accounts.forEach(this::display);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void display(Hotel hotel) {
        System.out.printf("Name: %s\nLocation: %s\nReserved: %s\nPersons: %d", hotel.getHotelName(), hotel.getLocation(), hotel.isReserved() ? "Yes"  : "No", hotel.getPersons());
    }
}
