package com.maccuci.hotel.backend.redis;

import com.maccuci.hotel.backend.Database;
import com.maccuci.hotel.manager.logger.MLogger;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class RedisDatabase implements Database {

    private JedisPool jedisPool;
    private final String hostName;
    private final int port;
    private final MLogger logger = new MLogger("REDIS");

    public RedisDatabase(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    @Override
    public void connect() {
        this.jedisPool = new JedisPool(this.hostName, this.port);
    }

    @Override
    public void disconnect() {
        jedisPool.close();
        logger.log("The Redis connection was closed.");
    }

    @Override
    public boolean isConnected() {
        return !jedisPool.isClosed();
    }

    public void keyAndValue(String key, String value) {
        try {
            if(isConnected()) {
                getJedisPool().getResource().set(key, value);
                logger.log(MLogger.Level.SUCCESS, String.format("The key %s was set as %s", key, value));
            }
        } catch (JedisConnectionException exception) {
            exception.printStackTrace();
        }
    }

    public void keyAndValuePerMap(Map<String, String> mappedParameters) {
        try {
            if(isConnected()) {
                getJedisPool().getResource().hmset("mapped", mappedParameters);
                logger.log(MLogger.Level.SUCCESS, String.format("The map %s was set as %s", mappedParameters.keySet(), mappedParameters.values()));
            }
        } catch (JedisConnectionException exception) {
            exception.printStackTrace();
        }
    }

    public String getValue(String key) {
        String value = getJedisPool().getResource().get(key);
        if (value == null) {
            return "Key not found.";
        }
        return value;
    }

    public String getValueMapped(String map) {
        Map<String, String> retrievedMap = this.jedisPool.getResource().hgetAll(map);
        AtomicReference<String> atomicValue = new AtomicReference<>("");
        retrievedMap.forEach((key1, value1) -> {
            if (atomicValue.get() == null) {
                atomicValue.set("Key not found.");
            }
            atomicValue.set(getJedisPool().getResource().get(value1));
        });
        return atomicValue.get();
    }


    public boolean isKeyAlreadyExists(String key) {
        if(!isConnected())
            return false;
        return getJedisPool().getResource().exists(key);
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }
}
