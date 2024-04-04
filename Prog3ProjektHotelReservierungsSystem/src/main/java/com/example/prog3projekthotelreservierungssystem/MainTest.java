package com.example.prog3projekthotelreservierungssystem;

import com.example.database.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainTest {
    private final Scanner scanner;
    private Zimmer zimmer1;
    private Mitarbeiter m1;
    //private LocalDate datum;
    private List<Buchung> buchungList;
    private static final int ENDE = 0;
    private static final int BUCHEN = 1;
    private static final int INFOS = 2;

    public MainTest() throws HotelException {
        scanner = new Scanner(System.in);
        buchungList = new ArrayList<>();
        addZimmer();
        addMitarbeiter();
    }

    private void start() throws HotelException {
        int zahl = -1;
        do {
            try {
                zahl = einlesenFunktion();
                funktionsAuswahl(zahl);
            } catch (HotelException e) {
                e.printStackTrace();
            }
        } while (zahl != ENDE);
    }

    private int einlesenFunktion() {
        System.out.println(BUCHEN + ": Buchen\n" +
                INFOS + ": Informationen anzeigen\n" +
                ENDE + ": Programm beenden");
        return scanner.nextInt();
    }

    private void funktionsAuswahl(int zahl) throws HotelException {
        if (zahl == ENDE) {
            System.out.println("Programm beendet");
        }
        if (zahl == BUCHEN) {
            buche();
        }
        if (zahl == INFOS) {
            System.out.println(zimmer1);
        }
    }

    private void buche() throws HotelException {
        System.out.println("Gast hinzufuegen");
        Person gast = addGast();
        System.out.println("DatumBeginn");
        LocalDate datumBeginn = addDatum();
        System.out.println("DatumEnde");
        LocalDate datumEnde = addDatum();

        Buchung buchung = new Buchung(gast, datumBeginn, datumEnde, zimmer1.getZimmerNr());

        //Zimmer merged = JDBCConnector.getSession().merge(zimmer1);
        zimmer1.buchungHinzufuegen(buchung);
        zimmer1.buchungHinzufuegen(buchung);
        Rechnung rechnung = new Rechnung(zimmer1.getPreis(), LocalDate.now(), Rechnung.Status.NICHT_BEZAHLT);
        buchung.setRechnung(rechnung);
        try {
            ZimmerConnector zimmerConnector = new ZimmerConnector();
            zimmerConnector.datenbankErstellen(zimmer1);

            PersonConnector personConnector = new PersonConnector();
            personConnector.datenbankErstellen(gast);

            RechnungConnector rechnungConnector = new RechnungConnector();
            rechnungConnector.datenbankErstellen(rechnung);

            BuchungConnector buchungConnector = new BuchungConnector();
            buchungConnector.datenbankErstellen(buchung);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HotelException("Fehler beim Speichern der Daten");
        }
    }

    private void addMitarbeiter() throws HotelException {
        String vorname = "Mhd Omran";
        String name = "Alrahban";
        LocalDate datum = LocalDate.of(2001, 12, 5);
        String telefonNr = "015215150841";
        m1 = new Mitarbeiter(vorname, name, "test@gmail.com", datum, telefonNr);
    }

    private void addZimmer() throws HotelException {
        zimmer1 = new Zimmer(1234, 1, 60, 200.0);
    }

    private LocalDate addDatum() {
        System.out.println("Tag eingeben");
        int tag = scanner.nextInt();
        System.out.println("Monat eingeben");
        int monat = scanner.nextInt();
        System.out.println("Jahr eingeben");
        int jahr = scanner.nextInt();
        return LocalDate.of(jahr, monat, tag);
    }

    private Person addGast() throws HotelException {
        scanner.nextLine();
        System.out.println("Gast Vorname eingeben");
        String vorname = scanner.nextLine();
        System.out.println("Gast Nachname eingeben");
        String name = scanner.nextLine();
        System.out.println("Gast Email eingeben");
        String email = scanner.nextLine();
        System.out.println("Gast Geburtsdatum eingeben");
        LocalDate geburtsdatum = addDatum();
        System.out.println("Gast TelefonNr eingeben");
        scanner.nextLine();
        String telefonNr = scanner.nextLine();
        return new Gast(vorname, name, email,
                geburtsdatum
                , telefonNr);
    }

    public static void main(String[] args) throws HotelException {
        new MainTest().start();
    }
}
