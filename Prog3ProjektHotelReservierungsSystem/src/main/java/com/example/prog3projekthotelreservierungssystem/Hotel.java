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
    private List<Mitarbeiter> mitarbeiter;
    private List<Gast> gaeste;

    public Hotel(int hotelBewertung) throws HotelException {
        if (hotelBewertung < 1 || hotelBewertung > 5) {
            throw new HotelException("Hotelbewertung muss zwischen 1 und 5 sein");
        }
        this.hotelBewertung = hotelBewertung;
        this.zimmern = new ArrayList<>();
        this.mitarbeiter = new ArrayList<>();
        this.gaeste = new ArrayList<>();
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
        return "HotelBewertung: " + hotelBewertung;
    }
}

