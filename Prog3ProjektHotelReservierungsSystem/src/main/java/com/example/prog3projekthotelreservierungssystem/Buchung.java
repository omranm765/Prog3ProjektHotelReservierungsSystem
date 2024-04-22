package com.example.prog3projekthotelreservierungssystem;

import com.example.database.BuchungConnector;
import com.example.database.RechnungConnector;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    //die funktioniert nicht beim löschen wenn nullabe = false deswegen auf true gesetzt;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "zimmer_id", nullable = true)
    private Zimmer zimmer;
    //die funktioniert nicht beim löschen wenn nullabe = false deswegen auf true gesetzt;
    @Column(name = "datum_beginn", nullable = false)
    private LocalDate buchungDatumBeginn;
    @Column(name = "datum_ende", nullable = false)
    private LocalDate buchungDatumEnde;
    @Column(nullable = true)
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
        Validator.check(gast == null, "Gast existiert nichtt");
        Validator.check(buchungDatumBeginn == null || buchungDatumEnde == null
                || buchungDatumBeginn.isAfter(buchungDatumEnde)
                || buchungDatumBeginn.isBefore(LocalDate.now()), "Ungültige Buchungsdaten");
        this.gast = gast;
        this.buchungDatumBeginn = buchungDatumBeginn;
        this.buchungDatumEnde = buchungDatumEnde;
        this.zimmerNr = zimmerNr;
    }

    public Buchung(){

    }
    public void rechnungErstellen() throws HotelException {
        if (this.storniert){
            throw new HotelException("Buchung ist Storniert");
        }
        if (!istGueltig()) {
            throw new HotelException("Buchung ist nicht gültig für Rechnungserstellung.");
        }
        LocalDate date1 = this.buchungDatumBeginn;
        LocalDate date2 = this.buchungDatumEnde;

        long diffInDays = ChronoUnit.DAYS.between(date1, date2);
        int diffInDaysInt = Math.abs((int) diffInDays);


        Rechnung rechnung1 = this.getRechnung();
        int rechnungID = rechnung1.getRechnungsID();

        StringBuilder sb = new StringBuilder();

        sb.append("Kunde: ").append(this.getGast());
        sb.append("ZimmerNr: ").append(this.getZimmer().getZimmerNr()).append("\n");
        sb.append("Rechnungsnummer: ").append(rechnungID).append("\n");
        sb.append("Buchungsdatum: ").append(rechnung1.getErstellungsDatum()).append("\n");
        sb.append("Betrag: ").append(this.getZimmer().getPreis() * diffInDaysInt).append(" EUR\n");
        sb.append("Buchung von: ").append(this.buchungDatumBeginn).append(" - bis: ")
                .append(this.buchungDatumEnde);
        String rechnungsText = sb.toString();

        String dateiname = "Rechnung_" + rechnungID + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dateiname))) {
            writer.write(rechnungsText);
            System.out.println("Rechnung erfolgreich erstellt: " + dateiname);
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern der Rechnung: " + e.getMessage());
        }
    }

    private boolean istGueltig() {
        if (this.gast == null) {
            System.out.println("Gast fehlt");
            return false;
        }
        if (this.buchungDatumBeginn == null || this.buchungDatumEnde == null
                || this.buchungDatumBeginn.isAfter(this.buchungDatumEnde)) {
            System.out.println("Ungültige Buchungsdaten");
            return false;
        }
        if (this.zimmerNr <= 0) {
            System.out.println("Ungültige Zimmernummer");
            return false;
        }
        return true;
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
        //Weil Zimmer und Buchung sind OneToOne das heißt wenn ein foreign key von Zimmer bei buchung
        //Gibt, kann keine neue Buchung für diese Zimmer erstellt werden auch wenn sie Storniert ist
        this.setZimmer(null);
        BuchungConnector buchungConnector = new BuchungConnector();
        buchungConnector.datenbankAktualisieren(this);
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
    public void setStorniert(boolean storniert){
        this.storniert = storniert;
    }


    @Override
    public String toString() {
        return "\nBuchung " +
                "\nGast: " + (!storniert ?  gast : "Hat die Buchung storniert") +
                "\nZimmerNr: " + zimmerNr +
                (!storniert ? "\nPreis: " + zimmer.getPreis() : "") +
                "\nBuchungDatumBegin: " + buchungDatumBeginn +
                "\nBuchungDatumEnde: " + buchungDatumEnde +
                "\nStorniert: " + (!storniert ? "Nein" : "Ja");
    }
}

