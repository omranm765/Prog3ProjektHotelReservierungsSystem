/**
 * Diese Klasse repräsentiert einen Gast im Hotel.
 */
package com.example.prog3projekthotelreservierungssystem;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "gast")
public class Gast extends Person{

    /**
     * Konstruktor für einen Gast.
     *
     * @param vorname      Der Vorname des Gastes.
     * @param name         Der Nachname des Gastes.
     * @param email        Die E-Mail-Adresse des Gastes.
     * @param geburtsdatum Das Geburtsdatum des Gastes.
     * @param telefonNr    Die Telefonnummer des Gastes.
     * @throws HotelException Wenn die Initialisierung fehlschlägt.
     */
    public Gast(String vorname, String name, String email, LocalDate geburtsdatum, String telefonNr) throws HotelException {
        super(vorname, name, email, geburtsdatum, telefonNr);
    }
    public Gast(){
        super();
    }

    /**
     * Gibt eine textuelle Repräsentation des Gastes zurück.
     *
     * @return Eine Zeichenkette, die den Gast repräsentiert.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
