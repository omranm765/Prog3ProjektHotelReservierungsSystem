package com.example.prog3projekthotelreservierungssystem;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
@Table(name ="Gast")
@Entity
public class Gast extends Person{
    public Gast(String vorname, String name, String email, LocalDate geburtsdatum, String telefonNr) throws HotelException {
        super(vorname, name, email, geburtsdatum, telefonNr);
    }

    //HALLO
    @Override
    public String toString() {
        return super.toString() + "\nGastID: ";
    }
}
