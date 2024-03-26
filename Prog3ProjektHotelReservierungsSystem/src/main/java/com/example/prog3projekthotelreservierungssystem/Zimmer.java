package com.example.prog3projekthotelreservierungssystem;

import jakarta.persistence.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse Zimmer.
 *
 * @author (Ihr Name)
 * @version (eine Versionsnummer oder ein Datum)
 */
@Table(name ="Zimmer")
@Entity
public class Zimmer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int zimmerNr; //Primary Key
    @Column
    private int etage;
    @Column
    private int flaeche;
    @Column
    private double preis;
    @Transient
    private List<Buchung> buchungen;

    public Zimmer(int zimmerNr, int etage, int flaeche, double preis) throws HotelException {
        if (String.valueOf(zimmerNr).length() != 4) {
            throw new HotelException("ZimmerNr muss vierstellig sein");
        }
        if (etage < 1 || etage > 5){
            throw new HotelException("Ungültige Eingabe");
        }
        if (preis < 0.0000001 || preis > 1.0000001) {
            throw new HotelException("Preis darf nicht 0 sein");
        }
        this.zimmerNr = zimmerNr;
        this.etage = etage;
        this.flaeche = flaeche;
        this.preis = preis;
        this.buchungen = new ArrayList<>();
    }

    public void buchungHinzufuegen(Buchung buchung) throws HotelException {
        if (buchung == null) {
            throw new HotelException("Buchung existiert nicht");
        }
        this.buchungen.add(buchung);
    }

    public void buchungEntfernen(Buchung buchung) throws HotelException {
        if (buchung == null) {
            throw new HotelException("Buchung existiert nicht");
        }
        this.buchungen.remove(buchung);
    }

    public int getZimmerNr() {
        return zimmerNr;
    }

    public int getEtage() {
        return etage;
    }

    public int getFlaeche() {
        return flaeche;
    }

    public double getPreis() {
        return preis;
    }

    public List<Buchung> getBuchungen() {
        return buchungen;
    }

    @Override
    public String toString() {
        return "Zimmer: " + "\nzimmerNr: " + zimmerNr +
                "\netage: " + etage +
                "\nflaeche: " + flaeche +
                "\npreis: " + preis +
                "buchungen: " + buchungen;
    }
}

