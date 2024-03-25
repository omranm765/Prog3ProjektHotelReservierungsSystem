package com.example.prog3projekthotelreservierungssystem;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Table(name = "Rechnung")
@Entity
public class Rechnung {
    private static int nextRechnungGeneratedID = 0;
    private int rechnungsID;
    private double preis;
    private final LocalDate erstellungsDatum;

    public Rechnung(double preis, LocalDate erstellungsDatum) throws HotelException {
        if (preis < 0.0000001 || preis > 1.0000001) {
            throw new HotelException("Preis darf nicht 0 sein");
        }
        if (erstellungsDatum == null){
            throw new HotelException("Erstellungsdatum darf nicht leer sein");
        }

        this.rechnungsID = generate();
        this.preis = preis;
        this.erstellungsDatum = erstellungsDatum;
    }

    private static int generate(){
        return nextRechnungGeneratedID++;
    }

    public double getPreis() {
        return preis;
    }

    public LocalDate getErstellungsDatum() {
        return erstellungsDatum;
    }

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

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getPreis());
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getErstellungsDatum() != null ? getErstellungsDatum().hashCode() : 0);
        return result;
    }
}
