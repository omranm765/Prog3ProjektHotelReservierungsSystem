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
    @JoinColumn(name = "zimmer_id", referencedColumnName = "buchung_id")
    private int zimmerId;

    @Column
    private int zimmerNr;
    @Column
    private int etage;
    @Column
    private int flaeche;
    @Column
    private double preis;
    @Transient
    private List<Buchung> buchungen;

    public Zimmer(int zimmerNr, int etage, int flaeche, double preis) throws HotelException {
        Validator.check(etage < 1 || etage > 5, "Ungültige Eingabe");
        Validator.check(preis < 0, "Preis darf nicht kleiner 0 sein");
        this.zimmerNr = zimmerNr;
        this.etage = etage;
        this.flaeche = flaeche;
        this.preis = preis;
        this.buchungen = new ArrayList<>();
    }

    public Zimmer(){
        this.buchungen = new ArrayList<>();
    }
    public void buchungHinzufuegen(Buchung buchung) throws HotelException {
        Validator.check(buchung == null, "Buchung existiert nicht");
        this.buchungen.add(buchung);
    }

    public void buchungEntfernen(Buchung buchung) throws HotelException {
        Validator.check(buchung == null, "Buchung existiert nicht");
        this.buchungen.remove(buchung);
        buchung.buchungStornieren();
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

    public void setBuchungen(List<Buchung> buchungen) {
        this.buchungen = buchungen;
    }

    @Override
    public String toString() {
        return "Zimmer: " + "\nzimmerNr: " + zimmerNr +
                "\netage: " + etage +
                "\nflaeche: " + flaeche +
                "\npreis: " + preis +
                "\nbuchungen: " + buchungen;
    }
}

