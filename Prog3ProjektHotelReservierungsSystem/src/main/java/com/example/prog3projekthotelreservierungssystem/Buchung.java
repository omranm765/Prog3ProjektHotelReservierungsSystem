package com.example.prog3projekthotelreservierungssystem;

import com.example.database.BuchungConnector;
import com.example.database.RechnungConnector;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
        Validator.check(gast == null, "Gast existiert nichtt");
        Validator.check(buchungDatumBeginn == null || buchungDatumEnde == null
                || buchungDatumBeginn.isAfter(buchungDatumEnde), "Ungültige Buchungsdaten");
        this.gast = gast;
        this.buchungDatumBeginn = buchungDatumBeginn;
        this.buchungDatumEnde = buchungDatumEnde;
        this.zimmerNr = zimmerNr;
    }

    public Buchung(){

    }
    public void rechnungErstellen() throws HotelException {
        if (!istGueltig()) {
            System.err.println("Buchung ist nicht gültig für Rechnungserstellung.");
            return;
        }
        BuchungConnector buchungConnector = new BuchungConnector();
        int rechnungID = buchungConnector.getRechnungIdForBuchung(this.getBuchungID());
        RechnungConnector rechnungConnector = new RechnungConnector();
        Rechnung rechnung1 = rechnungConnector.datenbankSuchNachId(rechnungID);

        StringBuilder sb = new StringBuilder();

        sb.append("Kunde: ").append(this.getGast());
        sb.append("Rechnungsnummer: ").append(rechnungID).append("\n");
        sb.append("Buchungsdatum: ").append(rechnung1.getErstellungsDatum()).append("\n");
        sb.append("Betrag: ").append(this.getZimmer().getPreis()).append(" EUR\n");
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
            System.err.println("Gast fehlt.");
            return false;
        }
        if (this.buchungDatumBeginn == null || this.buchungDatumEnde == null
                || this.buchungDatumBeginn.isAfter(this.buchungDatumEnde)) {
            System.err.println("Ungültige Buchungsdaten.");
            return false;
        }
        if (this.zimmerNr <= 0) {
            System.err.println("Ungültige Zimmernummer");
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
        return "\nBuchung " + "\ngast: " + gast +
                "\nzimmerNr: " + zimmerNr + "," +
                "\nbuchungDatumBegin: " + buchungDatumBeginn +
                "\nbuchungDatumEnde: " + buchungDatumEnde +
                "\nStorniert: " + (!storniert ? "Nein" : "Ja");
    }
}

