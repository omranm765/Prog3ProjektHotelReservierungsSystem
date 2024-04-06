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

    private static List<Zimmer> zimmern;
    private static List<Person> mitarbeitern;
    private static List<Person> gaeste;

    public Hotel() throws HotelException {



        zimmern = new ArrayList<>();
        mitarbeitern = new ArrayList<>();
        gaeste = new ArrayList<>();
    }

    public static void zimmerHinzufuegen(Zimmer zimmer) throws HotelException {
        if (zimmer == null){
            throw new HotelException("Zimmer existiert nicht");
        }
        zimmern.add(zimmer);
    }

    public static void gastHinzufuegen(Person gast) throws HotelException {
        if (gast == null){
            throw new HotelException("Gast existiert nicht");
        }
        gaeste.add(gast);
    }

    public static void mitarbeiterHinzufuegen(Person mitarbeiter) throws HotelException {
        if (mitarbeiter == null){
            throw new HotelException("Mitarbeiter existiert nicht");
        }
        mitarbeitern.add(mitarbeiter);
    }

    public static Buchung buchungErstellen(Gast gast, LocalDate buchungDatumBeginn,
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
        return
                "\nZimmer: " + zimmern +
                        "\nMitarbeiter: " + mitarbeitern +
                        "\nGaeste: " + gaeste;
    }
}