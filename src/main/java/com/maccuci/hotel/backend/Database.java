package com.maccuci.hotel.backend;

public interface Database {

    void connect();
    void disconnect();
    boolean isConnected();
}
