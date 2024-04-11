package com.example.prog3projekthotelreservierungssystem;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
/**
 * Diese Klasse repräsentiert einen Mitarbeiter im Hotel.
 * wir haben sonst mit dieser Klasse nichts gemacht.
 * Sie hat einen Gui aber wir benutzen es nicht.
 */
@Table(name ="mitarbeiter")
@Entity
public class Mitarbeiter extends Person{

    /**
     * Konstruktor für einen Mitarbeiter.
     *
     * @param vorname      Der Vorname des Mitarbeiters.
     * @param name         Der Nachname des Mitarbeiters.
     * @param email        Die E-Mail-Adresse des Mitarbeiters.
     * @param geburtsdatum Das Geburtsdatum des Mitarbeiters.
     * @param telefonNr    Die Telefonnummer des Mitarbeiters.
     * @throws HotelException Wenn einer der übergebenen Parameter ungültig ist.
     */
    public Mitarbeiter(String vorname, String name, String email, LocalDate geburtsdatum, String telefonNr) throws HotelException {
        super(vorname, name, email, geburtsdatum, telefonNr);
    }
    /**
     * Gibt eine Zeichenfolge mit den Informationen des Mitarbeiters zurück.
     *
     * @return Eine Zeichenfolge, die den Namen und andere Details des Mitarbeiters enthält.
     */
    @Override
    public String toString(){
       return super.toString();
    }
}