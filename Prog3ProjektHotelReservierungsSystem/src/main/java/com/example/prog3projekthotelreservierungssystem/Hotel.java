package com.example.prog3projekthotelreservierungssystem;

import com.example.database.*;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.ArrayList;
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

    //Da die meisten Methoden Static sind
    static {
        zimmern = new ArrayList<>();
        mitarbeitern = new ArrayList<>();
        gaeste = new ArrayList<>();
    }

    /**
     * Fügt ein Zimmer zur Liste der Hotelzimmer und im Datenbank hinzu.
     *
     * @param zimmer Das hinzuzufügende Zimmer.
     * @throws HotelException Wenn das Zimmer nicht existiert.
     */
    public static void zimmerHinzufuegen(Zimmer zimmer) throws HotelException {
        Validator.check(zimmer == null, "Zimmer existiert nicht");
        ZimmerConnector zimmerConnector = new ZimmerConnector();
        List<Zimmer> tempZimmer = getAllZimmer();
        for (Zimmer z : tempZimmer) {
            Validator.check(z.getZimmerNr() == zimmer.getZimmerNr(),
                    "Es existiert bereits ein Zimmer mit diesen ZimmerNr");
        }
        zimmern.add(zimmer);
        zimmerConnector.datenbankErstellen(zimmer);
    }

    /**
     * Entfernt der Zimmer aus der Liste sowie der Datenbank
     *
     * @param zimmer Das zu entfernende Zimmer.
     * @throws HotelException Wenn das Zimmer nicht existiert.
     */
    public static void zimmerEntfernen(Zimmer zimmer) throws HotelException {
        try {
            Validator.check(zimmer == null, "Zimmer existiert nicht");

            BuchungConnector buchungConnector = new BuchungConnector();
            List<Buchung> buchungen = getAllBuchungen();
            for (Buchung buchung : buchungen) {
                if (buchung.getZimmer() != null) {
                    if (buchung.getZimmer().getId() == zimmer.getId()) {
                        /*if statements getrennt, weil wir hier ein problem beim debuggen hatten.
                        problem war in der Klasse Zimmer bei der id annotation,
                        und zwar zimmerid war auf referencedColumnName = "buchung_id" gesetzt und
                        dürfte deswegen nicht löschen, was uns verrückt gemacht hat🥲
                        wir lassen es getrennt zur sicherheit 😅*/
                        if (buchung.isStorniert()) {
                            buchung.setZimmer(null);
                            //buchung.setZimmerNr(-1); Hatten wir vorher so stehehn gehabt
                            //Aber ist unnötig weil man weiß sonst nicht was storniert wurde
                            aktualisiereBuchung(buchung);
                        } else {
                            throw new HotelException("Die Buchung ist nicht storniert");
                        }
                    }
                }
            }
            ZimmerConnector zimmerConnector = new ZimmerConnector();
            zimmern.remove(zimmer);
            loescheZimmerId(zimmer.getId());
        } catch (HotelException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Die Buchung ist nicht storniert");
            alert.showAndWait();
        }
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
     * Entfernt den Gast aus der Liste sowie der Datenbank
     *
     * @param gast Das hinzuzufügende Zimmer.
     * @throws HotelException Wenn der Gast nicht existiert.
     */
    public static void GastEntfernen(Person gast) throws HotelException {
        try {
            Validator.check(gast == null, "Gast existiert nicht");
            BuchungConnector buchungConnector = new BuchungConnector();
            List<Buchung> buchungen = getAllBuchungen();
            for (Buchung buchung : buchungen) {
                if (buchung.getGast() != null && buchung.getGast().equals(gast)) {
                    if (!buchung.isStorniert()) {
                        throw new HotelException("Die Buchung ist nicht storniert");
                    }
                    if (buchung.getGast() != null && buchung.getGast().equals(gast) && buchung.isStorniert()) {
                        buchung.setGast(null);
                        aktualisiereBuchung(buchung);
                    }
                }
            }
            PersonConnector personConnector = new PersonConnector();
            gaeste.remove(gast);
            loeschePersonId(gast.getId());
        } catch (HotelException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public static void gastAendern(Person gast, String neuerVorname, String neuerNachname,
                                   String neueEmail, LocalDate neuesGeburtsdatum,
                                   String neueTelefonNr) throws HotelException {
        Validator.check(gast == null, "Gast existiert nicht");
        gast.setVorname(neuerVorname);
        gast.setName(neuerNachname);
        gast.setEmail(neueEmail);
        gast.setGeburtsdatum(neuesGeburtsdatum);
        gast.setTelefonNr(neueTelefonNr);
        aktualisierePersonen(gast);

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
    public static Buchung buchungErstellen(Person gast, LocalDate buchungDatumBeginn,
                                           LocalDate buchungDatumEnde, int zimmerNr) throws HotelException {
        return new Buchung(gast, buchungDatumBeginn, buchungDatumEnde, zimmerNr);
    }

    /**
     * Fügt der Zimmer zur der Liste sowie der Datenbank hinzu.
     *
     * @param buchung Das hinzuzufügende Zimmer.
     * @throws HotelException Wenn das Zimmer nicht existiert.
     */
    public static void buchungHinzufuegen(Buchung buchung) throws HotelException {
        Validator.check(buchung == null, "Buchung existiert nicht");
        Zimmer zimmer = null;
        ZimmerConnector zimmerConnector = new ZimmerConnector();
        List<Zimmer> tempZimmer = getAllZimmer();
        for (Zimmer z : tempZimmer) {
            if (z.getZimmerNr() == buchung.getZimmerNr()) {
                zimmer = z;
                break;
            }
        }
        Validator.check(zimmer == null, "Zimmer für Buchung nicht gefunden");
        zimmer = JDBCConnector.getSession().merge(zimmer);
        zimmer.buchungHinzufuegen(buchung);
        Rechnung rechnung = new Rechnung(zimmer.getPreis(), LocalDate.now(), Rechnung.Status.NICHT_BEZAHLT);
        RechnungConnector rechnungConnector = new RechnungConnector();
        rechnungConnector.datenbankErstellen(rechnung);
        buchung.setRechnung(rechnung);
        buchung.setZimmer(zimmer);
        BuchungConnector buchungConnector = new BuchungConnector();
        buchungConnector.datenbankErstellen(buchung);
    }

    public static List<Person> getAllGasts() {
        PersonConnector personConnector = new PersonConnector();
        return (List<Person>) personConnector.datenbankSuchAlles();
    }

    public static List<Zimmer> getAllZimmer() {
        ZimmerConnector zimmerConnector = new ZimmerConnector();
        return (List<Zimmer>) zimmerConnector.datenbankSuchAlles();
    }

    public static List<Rechnung> getAllRechnungen() {
        RechnungConnector rechnungConnector = new RechnungConnector();
        return (List<Rechnung>) rechnungConnector.datenbankSuchAlles();
    }

    public static List<Buchung> getAllBuchungen() {
        BuchungConnector buchungConnector = new BuchungConnector();
        return (List<Buchung>) buchungConnector.datenbankSuchAlles();
    }

    public static Person getPersonById(int gastId) {
        PersonConnector personConnector = new PersonConnector();
        return personConnector.datenbankSuchNachId(gastId);
    }

    public static Buchung getBuchungById(int buchungId) {
        BuchungConnector buchungConnector = new BuchungConnector();
        return buchungConnector.datenbankSuchNachId(buchungId);
    }

    public static Rechnung getRechnungById(int rechnungId) throws HotelException {
        RechnungConnector rechnungConnector = new RechnungConnector();
        return rechnungConnector.datenbankSuchNachId(rechnungId);
    }

    public static Zimmer getZimmerById(int zimmerId) {
        ZimmerConnector zimmerConnector = new ZimmerConnector();
        return zimmerConnector.datenbankSuchNachId(zimmerId);
    }

    public static void loeschePersonId(int gastId) {
        PersonConnector personConnector = new PersonConnector();
        personConnector.datenbankLoeschNachId(gastId);
    }

    public static void loescheZimmerId(int zimmerId) {
        ZimmerConnector zimmerConnector = new ZimmerConnector();
        zimmerConnector.datenbankLoeschNachId(zimmerId);
    }

    public static void loescheRechnungId(int rechnungId) {
        RechnungConnector rechnungConnector = new RechnungConnector();
        rechnungConnector.datenbankLoeschNachId(rechnungId);
    }

    public static void loescheBuchungId(int buchungId) {
        BuchungConnector buchungConnector = new BuchungConnector();
        buchungConnector.datenbankLoeschNachId(buchungId);
    }

    public static void loescheAllePersonen() {
        PersonConnector personConnector = new PersonConnector();
        personConnector.datenbankLoeschAlles();
    }

    public static void loescheAlleZimmer() {
        ZimmerConnector zimmerConnector = new ZimmerConnector();
        zimmerConnector.datenbankLoeschAlles();
    }

    public static void loescheAlleRechnungen() {
        RechnungConnector rechnungConnector = new RechnungConnector();
        rechnungConnector.datenbankLoeschAlles();
    }

    public static void loescheAlleBuchungen() {
        BuchungConnector buchungConnector = new BuchungConnector();
        buchungConnector.datenbankLoeschAlles();
    }

    public static void aktualisierePersonen(Person person) {
        PersonConnector personConnector = new PersonConnector();
        personConnector.datenbankAktualisieren(person);
    }

    public static void aktualisiereZimmer(Zimmer zimmer) {
        ZimmerConnector zimmerConnector = new ZimmerConnector();
        zimmerConnector.datenbankAktualisieren(zimmer);
    }

    public static void aktualisiereRechnung(Rechnung rechnung) {
        RechnungConnector rechnungConnector = new RechnungConnector();
        rechnungConnector.datenbankAktualisieren(rechnung);
    }

    public static void aktualisiereBuchung(Buchung buchung) {
        BuchungConnector buchungConnector = new BuchungConnector();
        buchungConnector.datenbankAktualisieren(buchung);
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
    public static void buchungAendern(Gast gast, LocalDate buchungDatumBeginn, LocalDate buchungDatumEnde, int zimmerNr,
                               int buchungID, Buchung buchung) {
        buchung.setBuchungDatumBegin(buchungDatumBeginn);
        buchung.setBuchungDatumEnde(buchungDatumEnde);
        buchung.setGast(gast);
        buchung.setZimmerNr(zimmerNr);
        aktualisiereBuchung(buchung);
    }

    public static Zimmer getZimmerByNummer(int nr) throws HotelException {
        for (Zimmer z : zimmern) {
            if (nr == z.getZimmerNr()) {
                return z;
            }
        }
        throw new HotelException("Zimmer mit Nummer " + nr + " nicht gefunden");
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