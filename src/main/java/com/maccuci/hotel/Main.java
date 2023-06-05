package com.maccuci.hotel;

import com.maccuci.hotel.backend.redis.RedisDatabase;
import com.maccuci.hotel.backend.hibernate.HibernateConfiguration;

public class Main {

    public static void main(String[] args) {
        RedisDatabase redisDatabaseController = new RedisDatabase("127.0.0.1", 6379);
        HibernateConfiguration hibernateConfiguration = new HibernateConfiguration();

        hibernateConfiguration.instance();
        redisDatabaseController.connect();
    }
}
