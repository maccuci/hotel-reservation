package com.maccuci.hotel;

import com.maccuci.hotel.backend.redis.RedisDatabase;
import com.maccuci.hotel.controller.hotel.HotelController;

public class Main {

    private static final RedisDatabase redisDatabaseController = new RedisDatabase("127.0.0.1", 6379);

    public static void main(String[] args) {
        redisDatabaseController.connect();

        new HotelController().create("Teste", "Rua do Teste", false, 100);
    }

    public static RedisDatabase getRedisDatabaseController() {
        return redisDatabaseController;
    }
}
