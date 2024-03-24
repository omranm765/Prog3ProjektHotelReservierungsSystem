package com.example.prog3projekthotelreservierungssystem;

import java.time.LocalDate;

public class Gast extends Person{
    private static int nextGastGeneratedID = 0;
    private final int gastID;
    public Gast(String vorname, String name, String email, LocalDate geburtsdatum, String telefonNr) throws HotelException {
        super(vorname, name, email, geburtsdatum, telefonNr);
        this.gastID = generate();
    }

    private static int generate(){
        return nextGastGeneratedID++;
    }

    @Override
    public String toString() {
        return super.toString() + "\nGastID: " + gastID;
    }
}
