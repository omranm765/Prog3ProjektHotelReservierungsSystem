package com.example.prog3projekthotelreservierungssystem;

import java.time.LocalDate;

public class Mitarbeiter extends Person{
    public Mitarbeiter(String vorname, String name, String email, LocalDate geburtsdatum, String telefonNr) throws HotelException {
        super(vorname, name, email, geburtsdatum, telefonNr);
    }

    @Override
    public String toString(){
       return super.toString();
    }
}
