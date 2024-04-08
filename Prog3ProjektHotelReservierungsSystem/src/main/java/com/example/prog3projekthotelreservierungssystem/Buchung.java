package com.example.prog3projekthotelreservierungssystem;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
/**
 * Diese Klasse repräsentiert eine Buchung für ein Hotelzimmer.
 */

@Table(name = "buchung")
@Entity
public class Buchung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buchung_id")
    private int BuchungID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gast_id", nullable = true)
    private Person gast;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "zimmer_id", nullable = false)
    private Zimmer zimmer;
    @Column(name = "datum_beginn", nullable = false)
    private LocalDate buchungDatumBeginn;
    @Column(name = "datum_ende", nullable = false)
    private LocalDate buchungDatumEnde;
    @Column
    private int zimmerNr;

    @Column
    private boolean storniert = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rechnung_id", nullable = false)
    private Rechnung rechnung;

    /**
     * Konstruktor für eine Buchung.
     *
     * @param gast Der Gast, der die Buchung vornimmt.
     * @param buchungDatumBeginn Startdatum der Buchung.
     * @param buchungDatumEnde Enddatum der Buchung.
     * @param zimmerNr Die Zimmernummer für die Buchung.
     * @throws HotelException Wenn Buchungsdaten ungültig sind.
     */
    public Buchung(Person gast, LocalDate buchungDatumBeginn, LocalDate buchungDatumEnde,
                   int zimmerNr) throws HotelException {
        Validator.check(gast == null, "Gast existiert nicht");
        Validator.check(buchungDatumBeginn == null || buchungDatumEnde == null
                || buchungDatumBeginn.isAfter(buchungDatumEnde), "Ungültige Buchungsdaten");
        this.gast = gast;
        this.buchungDatumBeginn = buchungDatumBeginn;
        this.buchungDatumEnde = buchungDatumEnde;
        this.zimmerNr = zimmerNr;
    }

    /**
     * Storniert die Rechnung dieser Buchung.
     *
     * @param rechnung Die zu stornierende Rechnung.
     */
    public void rechnungStornieren(Rechnung rechnung) {
        rechnung = null;
    }
    /**
     * Bezahlt die Buchung mit einer Rechnung.
     *
     * @param bezahlDatum Das Datum der Zahlung.
     * @param status      Der Status der Rechnung.
     * @param preis       Der zu zahlende Betrag.
     * @throws HotelException Wenn das Zahlungsdatum ungültig ist.
     */
    public void bezahlen(LocalDate bezahlDatum, Rechnung.Status status, double preis) throws HotelException {

        Validator.check(bezahlDatum == null, "Ungültiges Zahlungsdatum");
        this.rechnung = new Rechnung(preis, bezahlDatum, Rechnung.Status.BEZAHLT);
    }

    /**
     * Storniert diese Buchung.
     */
    public void buchungStornieren() {
        storniert = true;
    }

    public void setGast(Gast gast) {
        this.gast = gast;
    }

    public void setBuchungDatumBegin(LocalDate buchungDatumBegin) {
        this.buchungDatumBeginn = buchungDatumBegin;
    }

    public void setBuchungDatumEnde(LocalDate buchungDatumEnde) {
        this.buchungDatumEnde = buchungDatumEnde;
    }

    public void setZimmerNr(int zimmerNr) {
        this.zimmerNr = zimmerNr;
    }

    public Person getGast() {
        return gast;
    }

    public LocalDate getBuchungDatumBeginn() {
        return buchungDatumBeginn;
    }

    public LocalDate getBuchungDatumEnde() {
        return buchungDatumEnde;
    }

    public int getZimmerNr() {
        return zimmerNr;
    }

    public int getBuchungID() {
        return BuchungID;
    }

    public Zimmer getZimmer() {
        return zimmer;
    }


    public boolean isStorniert() {
        return storniert;
    }

    public Rechnung getRechnung() {
        return rechnung;
    }

    public void setRechnung(Rechnung rechnung) {
        this.rechnung = rechnung;
    }

    public void setGast(Person gast) {
        this.gast = gast;
    }

    public void setZimmer(Zimmer zimmer) {
        this.zimmer = zimmer;
    }

    @Override
    public String toString() {
        return "\nBuchung " + "\ngast: " + gast +
                "\nzimmerNr: " + zimmerNr + "," +
                "\nbuchungDatumBegin: " + buchungDatumBeginn +
                "\nbuchungDatumEnde: " + buchungDatumEnde;
    }
}

