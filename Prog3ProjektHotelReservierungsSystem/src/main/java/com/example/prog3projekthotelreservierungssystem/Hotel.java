package com.example.prog3projekthotelreservierungssystem;

import com.example.database.PersonConnector;
import com.example.database.ZimmerConnector;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Diese Klasse repräsentiert ein Hotel.
 */
public class Hotel {

    private static List<Zimmer> zimmern;
    private static List<Person> mitarbeitern;
    private static List<Person> gaeste;


    /**
     * Konstruktor für ein Hotel.
     *
     * @throws HotelException Wenn die Initialisierung fehlschlägt.
     */
    public Hotel() throws HotelException {


        zimmern = new ArrayList<>();
        mitarbeitern = new ArrayList<>();
        gaeste = new ArrayList<>();
    }

    static {
        zimmern = new ArrayList<>();
        mitarbeitern = new ArrayList<>();
        gaeste = new ArrayList<>();
    }
    /**
     * Fügt ein Zimmer zur Liste der Hotelzimmer hinzu.
     *
     * @param zimmer Das hinzuzufügende Zimmer.
     * @throws HotelException Wenn das Zimmer nicht existiert.
     */
    public static void zimmerHinzufuegen(Zimmer zimmer) throws HotelException {
        Validator.check(zimmer == null, "Zimmer existiert nicht");
        zimmern.add(zimmer);
        ZimmerConnector zimmerConnector = new ZimmerConnector();
        zimmerConnector.datenbankErstellen(zimmer);
    }

    /**
     * Fügt einen Gast zur Liste der Hotelgäste hinzu.
     *
     * @param gast Der hinzuzufügende Gast.
     * @throws HotelException Wenn der Gast nicht existiert.
     */
    public static void gastHinzufuegen(Person gast) throws HotelException {
        Validator.check(gast == null, "Gast existiert nicht");
        gaeste.add(gast);
        PersonConnector personConnector = new PersonConnector();
        personConnector.datenbankErstellen(gast);
    }

    /**
     * Fügt einen Mitarbeiter zur Liste der Hotelmitarbeiter hinzu.
     *
     * @param mitarbeiter Der hinzuzufügende Mitarbeiter.
     * @throws HotelException Wenn der Mitarbeiter nicht existiert.
     */
    public static void mitarbeiterHinzufuegen(Person mitarbeiter) throws HotelException {
        Validator.check(mitarbeiter == null, "mitarbeiter existiert nicht");
        mitarbeitern.add(mitarbeiter);
        PersonConnector personConnector = new PersonConnector();
        personConnector.datenbankErstellen(mitarbeiter);
    }

    /**
     * Erstellt eine Buchung für einen Gast im Hotel.
     *
     * @param gast               Der Gast, der die Buchung vornimmt.
     * @param buchungDatumBeginn Startdatum der Buchung.
     * @param buchungDatumEnde   Enddatum der Buchung.
     * @param zimmerNr           Die Zimmernummer für die Buchung.
     * @return Die erstellte Buchung.
     * @throws HotelException Wenn die Buchung nicht erstellt werden kann.
     */
    public static Buchung buchungErstellen(Gast gast, LocalDate buchungDatumBeginn,
                                           LocalDate buchungDatumEnde, int zimmerNr) throws HotelException {
        return new Buchung(gast, buchungDatumBeginn, buchungDatumEnde, zimmerNr);
    }

    public static void buchungHinzufuegen(Buchung buchung) throws HotelException {
        Validator.check(buchung == null, "Buchung existiert nicht");
        Zimmer zimmer = null;
        for (Zimmer z : zimmern) {
            if (z.getZimmerNr() == buchung.getZimmerNr()) {
                zimmer = z;
                break;
            }
        }
        Validator.check(zimmer == null, "Zimmer für Buchung nicht gefunden");
        zimmer.buchungHinzufuegen(buchung);
    }

    /**
     * Ändert eine bestehende Buchung im Hotel.
     *
     * @param gast               Der Gast für die Buchung.
     * @param buchungDatumBeginn Neues Startdatum der Buchung.
     * @param buchungDatumEnde   Neues Enddatum der Buchung.
     * @param zimmerNr           Neue Zimmernummer für die Buchung.
     * @param buchungID          ID der zu ändernden Buchung.
     * @param buchung            Die zu ändernde Buchung.
     */
    public void buchungAendern(Gast gast, LocalDate buchungDatumBeginn, LocalDate buchungDatumEnde, int zimmerNr,
                               int buchungID, Buchung buchung) {
        buchung.setBuchungDatumBegin(buchungDatumBeginn);
        buchung.setBuchungDatumEnde(buchungDatumEnde);
        buchung.setGast(gast);
        buchung.setZimmerNr(zimmerNr);

    }

    /**
     * Gibt eine textuelle Repräsentation des Hotels zurück.
     *
     * @return Eine Zeichenkette, die das Hotel repräsentiert.
     */
    public String toString() {
        return "\nZimmer: " + zimmern +
                "\nMitarbeiter: " + mitarbeitern +
                "\nGaeste: " + gaeste;
    }
}