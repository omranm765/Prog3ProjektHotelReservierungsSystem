package com.example.prog3projekthotelreservierungssystem;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.time.LocalDate;

/**
 * Beschreiben Sie hier die Klasse Buchung.
 *
 * @author (Ihr Name)
 * @version (eine Versionsnummer oder ein Datum)
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

    public Buchung(Person gast, LocalDate buchungDatumBeginn, LocalDate buchungDatumEnde,
                   int zimmerNr) throws HotelException {
        if (gast == null) {
            throw new HotelException("Gast existiert nicht");
        }
        if (buchungDatumBeginn == null || buchungDatumEnde == null
                || buchungDatumBeginn.isAfter(buchungDatumEnde)) {
            throw new HotelException("Ungültige Buchungsdaten");
        }
        /*if (String.valueOf(zimmerNr).length() != 4) {
            throw new HotelException("ZimmerNr muss vierstellig sein");
        }*/
        this.gast = gast;
        this.buchungDatumBeginn = buchungDatumBeginn;
        this.buchungDatumEnde = buchungDatumEnde;
        this.zimmerNr = zimmerNr;
    }


    //database
    public void rechnungStornieren(Rechnung rechnung) {
        rechnung = null;
    }

    public void bezahlen(LocalDate bezahlDatum, Rechnung.Status status, double preis) throws HotelException {

        if (bezahlDatum == null) {
            throw new IllegalArgumentException("Ungültiges Zahlungsdatum");
        }
        this.rechnung = new Rechnung(preis, bezahlDatum, Rechnung.Status.BEZAHLT);
    }

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

