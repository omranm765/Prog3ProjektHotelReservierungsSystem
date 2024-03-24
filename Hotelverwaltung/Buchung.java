package com.example.prog3projekthotelreservierungssystem;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

/**
 * Beschreiben Sie hier die Klasse Buchung.
 *
 * @author (Ihr Name)
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Buchung {
    private static int nextBuchungGeneratedID = 0;
    private Gast gast;
    private LocalDate buchungDatumBeginn;
    private LocalDate buchungDatumEnde;
    private int zimmerNr;
    private final int BuchungID; // forgein key
    private List<Service> services;

    public Buchung(Gast gast, LocalDate buchungDatumBeginn, LocalDate buchungDatumEnde,
                   int zimmerNr) throws HotelException {
        if (gast == null) {
            throw new HotelException("Gast existiert nicht");
        }
        if (buchungDatumBeginn == null || buchungDatumEnde == null
                || buchungDatumBeginn.isAfter(buchungDatumEnde)) {
            throw new HotelException("Ungültige Buchungsdaten");
        }
        if (String.valueOf(zimmerNr).length() != 4) {
            throw new HotelException("ZimmerNr muss vierstellig sein");
        }
        this.gast = gast;
        this.buchungDatumBeginn = buchungDatumBeginn;
        this.buchungDatumEnde = buchungDatumEnde;
        this.zimmerNr = zimmerNr;
        this.BuchungID = generate();
        this.services = new ArrayList<>();
    }

    public static int generate() {
        return nextBuchungGeneratedID++;
    }

    //database
    public Rechnung rechnungErstellen(Rechnung rechnung) {
        return rechnung;
        // need to be implemented
    }

    //database
    public void rechnungStornieren(Rechnung rechnung) {
        // need to be implemented
    }

    public void serviceErstellen(Service service) throws HotelException {
        if (service == null) {
            throw new HotelException("service existiert nicht");
        }
        services.add(service);
    }

    public void serviceEntfernen(Service service) throws HotelException {
        if (service == null) {
            throw new HotelException("service existiert nicht");
        }
        services.remove(service);
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

    public Gast getGast() {
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

    public List<Service> getServices() {
        return services;
    }

    @Override
    public String toString() {
        return "Buchung: " + "\ngast: " + gast +
                "\nzimmerNr: " + zimmerNr + "," +
                "\nbuchungDatumBegin: " + buchungDatumBeginn +
                "\nbuchungDatumEnde: " + buchungDatumEnde +
                "\nBuchungID: " + BuchungID +
                "\nservices: " + services;
    }
}

