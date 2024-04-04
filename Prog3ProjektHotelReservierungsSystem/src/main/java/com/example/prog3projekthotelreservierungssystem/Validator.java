package com.example.prog3projekthotelreservierungssystem;

public class Validator {

    public Validator(){

    }

    public static void check(boolean statement, String message) throws HotelException {
        if (statement){
            throw new HotelException(message);
        }
    }
}
