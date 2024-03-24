package com.example.prog3projekthotelreservierungssystem;

import java.time.LocalDate;

public class Mitarbeiter extends Person{
    private static int nextMitarbeiterGeneratedID = 0;
    private final int mitarbeiterID;
    public Mitarbeiter(String vorname, String name, String email, LocalDate geburtsdatum, String telefonNr) throws HotelException {
        super(vorname, name, email, geburtsdatum, telefonNr);
        this.mitarbeiterID = generate();
    }

    private static int generate(){
        return nextMitarbeiterGeneratedID++;
    }
    @Override
    public String toString(){
       return super.toString() + "\nMitarbeiterID: " + mitarbeiterID;
    }
}