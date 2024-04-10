package com.example.prog3projekthotelreservierungssystem;

import jakarta.persistence.*;

import java.time.LocalDate;
/**
 * Diese Klasse repräsentiert eine Rechnung im Hotelreservierungssystem.
 */
@Entity
@Table(name = "Rechnung")
public class Rechnung {



    public enum Status{
        BEZAHLT, NICHT_BEZAHLT;
    }

    @Column
    private Status status;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rechnungsID;
    @Column(name = "rechnung_preis")
    private double preis;
    @Column(name = "rechnung_erstellungsdatum")
    private LocalDate erstellungsDatum;

    /**
     * Konstruktor für eine Rechnung.
     *
     * @param preis           Der Preis der Rechnung.
     * @param erstellungsDatum Das Erstellungsdatum der Rechnung.
     * @param status          Der Status der Rechnung (BEZAHLT oder NICHT_BEZAHLT).
     * @throws HotelException Wenn der Preis ungültig ist oder das Erstellungsdatum leer ist.
     */
    public Rechnung(double preis, LocalDate erstellungsDatum, Status status) throws HotelException {
        Validator.check(preis < 0.0000001 && preis > -0.0000001, "Preis darf nicht 0 sein");
        Validator.check(erstellungsDatum == null, "Erstellungsdatum darf nicht leer sein");

        this.preis = preis;
        this.erstellungsDatum = erstellungsDatum;
        this.status = status;
    }

    public Rechnung(){

    }


    public double getPreis() {
        return preis;
    }

    /**
     * Gibt das Erstellungsdatum der Rechnung zurück.
     *
     * @return Das Erstellungsdatum der Rechnung.
     */
    public LocalDate getErstellungsDatum() {
        return erstellungsDatum;
    }

    /**
     * Gibt eine String-Repräsentation der Rechnung zurück.
     *
     * @return Eine String-Repräsentation der Rechnung.
     */

    public String toString(){
        return "Rechnung\nPreis: " + preis + "\nErstellungsdatum: " + erstellungsDatum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rechnung)) return false;

        Rechnung rechnung = (Rechnung) o;

        if (Double.compare(rechnung.getPreis(), getPreis()) != 0) return false;
        return getErstellungsDatum() != null ? getErstellungsDatum().equals(rechnung.getErstellungsDatum()) : rechnung.getErstellungsDatum() == null;
    }

    public Status getStatus(){
        return status;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getPreis());
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getErstellungsDatum() != null ? getErstellungsDatum().hashCode() : 0);
        return result;
    }

    public int getRechnungsID() {
      return rechnungsID;
    }
}
