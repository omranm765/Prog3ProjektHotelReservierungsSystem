package com.example.prog3projekthotelreservierungssystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Beschreiben Sie hier die Klasse Hotel.
 *
 * @author (Ihr Name)
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Hotel {
    private int hotelBewertung;
    private List<Zimmer> zimmern;
    private List<Person> mitarbeitern;
    private List<Person> gaeste;

    public Hotel(int hotelBewertung) throws HotelException {
        if (hotelBewertung < 1 || hotelBewertung > 5) {
            throw new HotelException("Hotelbewertung muss zwischen 1 und 5 sein");
        }
        this.hotelBewertung = hotelBewertung;
        this.zimmern = new ArrayList<>();
        this.mitarbeitern = new ArrayList<>();
        this.gaeste = new ArrayList<>();
    }

    public void zimmerHinzufuegen(Zimmer zimmer) throws HotelException {
        if (zimmer == null){
            throw new HotelException("Zimmer existiert nicht");
        }
        zimmern.add(zimmer);
    }

    public void gastHinzufuegen(Person gast) throws HotelException {
        if (gast == null){
            throw new HotelException("Gast existiert nicht");
        }
        gaeste.add(gast);
    }

    public void mitarbeiterHinzufuegen(Person mitarbeiter) throws HotelException {
        if (mitarbeiter == null){
            throw new HotelException("Mitarbeiter existiert nicht");
        }
        mitarbeitern.add(mitarbeiter);
    }

    public Buchung buchungErstellen(Gast gast, LocalDate buchungDatumBeginn,
                                    LocalDate buchungDatumEnde, int zimmerNr) throws HotelException {
        return new Buchung(gast, buchungDatumBeginn, buchungDatumEnde, zimmerNr);
    }

    public void buchungAendern(Gast gast, LocalDate buchungDatumBeginn, LocalDate buchungDatumEnde, int zimmerNr,
                               int buchungID, Buchung buchung) {
        buchung.setBuchungDatumBegin(buchungDatumBeginn);
        buchung.setBuchungDatumEnde(buchungDatumEnde);
        buchung.setGast(gast);
        buchung.setZimmerNr(zimmerNr);

    }



    public String toString() {
        return "HotelBewertung: " + hotelBewertung +
                "\nZimmer: " + zimmern +
                "\nMitarbeiter: " + mitarbeitern +
                "\nGaeste: " + gaeste;
    }
}

