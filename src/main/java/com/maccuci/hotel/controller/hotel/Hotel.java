package com.maccuci.hotel.controller.hotel;

import javax.persistence.*;

@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String hotelName, location;
    private boolean reserved;
    private int persons;

    public Hotel(String hotelName, String location, boolean reserved, int persons) {
        this.hotelName = hotelName;
        this.location = location;
        this.reserved = reserved;
        this.persons = persons;
    }

    public int getId() {
        return id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getLocation() {
        return location;
    }

    public boolean isReserved() {
        return reserved;
    }

    public int getPersons() {
        return persons;
    }
}
