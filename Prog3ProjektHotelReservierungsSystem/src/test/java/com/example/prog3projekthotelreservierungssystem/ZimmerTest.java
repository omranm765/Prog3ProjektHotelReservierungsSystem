package com.example.prog3projekthotelreservierungssystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ZimmerTest {

    private Zimmer zimmer;
    private Buchung buchung1;
    private Buchung buchung2;

    @BeforeEach
    public void setUp() throws HotelException {
        zimmer = new Zimmer(1, 2, 20, 50);

        Person gast1 = new Gast("hoho", "momo", "hoho.momo@web.de", LocalDate.of(1996, 1, 1), "576756765");
         buchung1 = new Buchung(gast1, LocalDate.now(), LocalDate.now().plusDays(9), 1);

        Person gast2 = new Gast("fofo", "koko", "fofo.koko@web.de", LocalDate.of(1993, 1, 1), "43654346");
         buchung2 = new Buchung(gast2, LocalDate.now(), LocalDate.now().plusDays(8), 1);
    }

    @Test
    public void testBuchungHinzufuegen() throws HotelException {
        Person gast = new Gast("Hans", "Müller", "hans.mueller@web.de", LocalDate.of(1990, 1, 1), "1367836");
        Buchung buchung = new Buchung(gast, LocalDate.now(), LocalDate.now().plusDays(7), 1);

        Rechnung rechnung = new Rechnung(100, LocalDate.now(), Rechnung.Status.NICHT_BEZAHLT);
        buchung.setRechnung(rechnung);
        buchung.setZimmerNr(1);
        buchung.buchungStornieren();
        assertTrue(buchung.isStorniert());
        assertNull(buchung.getZimmer());

        buchung.bezahlen(LocalDate.now(), Rechnung.Status.NICHT_BEZAHLT, 100);
        assertEquals(rechnung, buchung.getRechnung());
    }


    @Test
    public void testBuchungEntfernen() throws HotelException {
        zimmer.buchungHinzufuegen(buchung1);
        zimmer.buchungHinzufuegen(buchung2);
        assertEquals(2, zimmer.getBuchungen().size());
        zimmer.buchungEntfernen(buchung1);
        assertEquals(1, zimmer.getBuchungen().size());
        buchung1.buchungStornieren();
        assertTrue(buchung1.isStorniert());
    }

    @Test
    public void testEquals() throws HotelException {
        Zimmer zimmer2 = new Zimmer(1, 2, 20, 50);
        assertEquals(zimmer, zimmer2);
    }

    @Test
    public void testHashCode() throws HotelException {
        Zimmer zimmer2 = new Zimmer(1, 2, 20, 50);
        assertEquals(zimmer.hashCode(), zimmer2.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "Zimmer: \nzimmerNr: 1\netage: 2\nflaeche: 20\npreis: 50.0";
        assertEquals(expectedToString, zimmer.toString());
    }
}