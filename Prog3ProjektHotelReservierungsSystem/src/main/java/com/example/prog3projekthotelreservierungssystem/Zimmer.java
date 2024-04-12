package com.example.prog3projekthotelreservierungssystem;

import jakarta.persistence.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Eine Klasse, die ein Zimmer im Hotel repräsentiert.
 */

@Table(name ="Zimmer")
@Entity
public class Zimmer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "zimmer_id")
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

    /**
     * Konstruktor für ein Zimmer im Hotel.
     *
     * @param zimmerNr Die Zimmernummer.
     * @param etage    Die Etage, auf der sich das Zimmer befindet.
     * @param flaeche  Die Fläche des Zimmers.
     * @param preis    Der Preis pro Nacht für das Zimmer.
     * @throws HotelException Wenn einer der übergebenen Parameter ungültig ist.
     */
    public Zimmer(int zimmerNr, int etage, int flaeche, double preis) throws HotelException {
        Validator.check(etage < 1 || etage > 5, "Ungültige Eingabe");
        Validator.check(preis < 0, "Preis darf nicht kleiner 0 sein");
        this.zimmerNr = zimmerNr;
        this.etage = etage;
        this.flaeche = flaeche;
        this.preis = preis;
        this.buchungen = new ArrayList<>();
    }
    /**
     * Standardkonstruktor für ein Zimmer.
     */
    public Zimmer(){
        this.buchungen = new ArrayList<>();
    }

    /**
     * Fügt eine Buchung zu diesem Zimmer hinzu.
     *
     * @param buchung Die hinzuzufügende Buchung.
     * @throws HotelException Wenn die Buchung ungültig ist.
     */
    public void buchungHinzufuegen(Buchung buchung) throws HotelException {
        Validator.check(buchung == null, "Buchung existiert nicht");
        this.buchungen.add(buchung);
    }
    /**
     * Entfernt eine Buchung aus diesem Zimmer und storniert sie.
     *
     * @param buchung Die zu entfernende Buchung.
     * @throws HotelException Wenn die Buchung ungültig ist.
     */
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

    /**
     * Gibt eine Zeichenfolge dar, die die Details des Zimmers enthält.
     *
     * @return Eine Zeichenfolge mit den Informationen zum Zimmer.
     */
    @Override
    public String toString() {
        return "Zimmer: " + "\nzimmerNr: " + zimmerNr +
                "\netage: " + etage +
                "\nflaeche: " + flaeche +
                "\npreis: " + preis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zimmer zimmer = (Zimmer) o;
        return zimmerId == zimmer.zimmerId && zimmerNr == zimmer.zimmerNr && etage == zimmer.etage && flaeche == zimmer.flaeche && Double.compare(preis, zimmer.preis) == 0 && Objects.equals(buchungen, zimmer.buchungen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zimmerId, zimmerNr, etage, flaeche, preis, buchungen);
    }

    public int getId(){
        return zimmerId;
    }
}

