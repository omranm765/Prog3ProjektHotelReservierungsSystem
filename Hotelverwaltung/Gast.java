package com.example.prog3projekthotelreservierungssystem;

import java.time.LocalDate;

public class Gast extends Person{
    public Gast(String vorname, String name, String email, LocalDate geburtsdatum, String telefonNr) throws HotelException {
        super(vorname, name, email, geburtsdatum, telefonNr);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
