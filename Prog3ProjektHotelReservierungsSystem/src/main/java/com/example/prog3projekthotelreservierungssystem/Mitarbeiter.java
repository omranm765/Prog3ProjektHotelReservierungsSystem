package com.example.prog3projekthotelreservierungssystem;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
@Table(name ="mitarbeiter")
@Entity
public class Mitarbeiter extends Person{
    public Mitarbeiter(String vorname, String name, String email, LocalDate geburtsdatum, String telefonNr) throws HotelException {
        super(vorname, name, email, geburtsdatum, telefonNr);
    }

    @Override
    public String toString(){
       return super.toString();
    }
}