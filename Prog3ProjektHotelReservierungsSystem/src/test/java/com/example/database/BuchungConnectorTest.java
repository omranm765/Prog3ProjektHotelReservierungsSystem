package com.example.database;

import com.example.prog3projekthotelreservierungssystem.*;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuchungConnectorTest {

    private BuchungConnector buchungConnector;

    @BeforeEach
    void setUp() {
        buchungConnector = new BuchungConnector();
    }

    @AfterEach
    void tearDown() {
        buchungConnector.datenbankLoeschAlles();
    }

    @Test
    void testDatenbankErstellen() throws HotelException {
        Person gast1 = new Gast("hoho", "momo", "hoho.momo@web.de", LocalDate.of(1996, 1, 1), "576756765");
        Buchung buchung = new Buchung(gast1, LocalDate.now(), LocalDate.now().plusDays(9), 1);
        assertDoesNotThrow(() -> buchungConnector.datenbankErstellen(buchung));
        Buchung foundBuchung = buchungConnector.datenbankSuchNachId(1);
        assertEquals(buchung, foundBuchung);
    }

    @Test
    void testDatenbankSuchAlles() throws HotelException {
        Person gast1 = new Gast("hoho", "momo", "hoho.momo@web.de", LocalDate.of(1996, 1, 1), "576756765");
        Buchung buchung1 = new Buchung(gast1, LocalDate.now(), LocalDate.now().plusDays(9), 1);

        Person gast2 = new Gast("fofo", "koko", "fofo.koko@web.de", LocalDate.of(1993, 1, 1), "43654346");
        Buchung buchung2 = new Buchung(gast2, LocalDate.now(), LocalDate.now().plusDays(8), 1);
        buchungConnector.datenbankErstellen(buchung1);
        buchungConnector.datenbankErstellen(buchung2);
        List<Buchung> allBuchungen = buchungConnector.datenbankSuchAlles();
        assertTrue(allBuchungen.contains(buchung1));
        assertTrue(allBuchungen.contains(buchung2));
    }

    @Test
    void testDatenbankSuchNachId() throws HotelException {
        Person gast1 = new Gast("hoho", "momo", "hoho.momo@web.de", LocalDate.of(1996, 1, 1), "576756765");
        Buchung buchung = new Buchung(gast1, LocalDate.now(), LocalDate.now().plusDays(9), 1);
        buchungConnector.datenbankErstellen(buchung);
        Buchung foundBuchung = buchungConnector.datenbankSuchNachId(1);
        assertEquals(buchung, foundBuchung);
    }

    @Test
    void testDatenbankLoeschAlles() throws HotelException {
        Person gast1 = new Gast("hoho", "momo", "hoho.momo@web.de", LocalDate.of(1996, 1, 1), "576756765");
        Buchung buchung1 = new Buchung(gast1, LocalDate.now(), LocalDate.now().plusDays(9), 1);

        Person gast2 = new Gast("fofo", "koko", "fofo.koko@web.de", LocalDate.of(1993, 1, 1), "43654346");
        Buchung buchung2 = new Buchung(gast2, LocalDate.now(), LocalDate.now().plusDays(8), 1);
        buchungConnector.datenbankErstellen(buchung1);
        buchungConnector.datenbankErstellen(buchung2);
        buchungConnector.datenbankLoeschAlles();
        List<Buchung> allBuchungen = buchungConnector.datenbankSuchAlles();
        assertTrue(allBuchungen.isEmpty());
    }

    @Test
    void testDatenbankLoeschNachId() throws HotelException {
        Person gast1 = new Gast("hoho", "momo", "hoho.momo@web.de", LocalDate.of(1996, 1, 1), "576756765");
        Buchung buchung = new Buchung(gast1, LocalDate.now(), LocalDate.now().plusDays(9), 1);
        buchungConnector.datenbankErstellen(buchung);
        buchungConnector.datenbankLoeschNachId(1);
        List<Buchung> allBuchungen = buchungConnector.datenbankSuchAlles();
        assertTrue(allBuchungen.isEmpty());
    }

    @Test
    void testDatenbankAktualisieren() throws HotelException {
        Person gast2 = new Gast("fofo", "koko", "fofo.koko@web.de", LocalDate.of(1993, 1, 1), "43654346");
        Buchung buchung = new Buchung(gast2, LocalDate.now(), LocalDate.now().plusDays(8), 1);
        buchungConnector.datenbankErstellen(buchung);
        buchung.setBuchungDatumBegin(LocalDate.now().plusDays(1));
        buchungConnector.datenbankAktualisieren(buchung);
        Buchung foundBuchung = buchungConnector.datenbankSuchNachId(1);
        assertEquals(LocalDate.now().plusDays(1), foundBuchung.getBuchungDatumBeginn());
    }

    @Test
    void testGetRechnungIdForBuchung() throws HotelException {
        Person gast1 = new Gast("hoho", "momo", "hoho.momo@web.de", LocalDate.of(1996, 1, 1), "576756765");
        Buchung buchung = new Buchung(gast1, LocalDate.now(), LocalDate.now().plusDays(9), 1);
        buchungConnector.datenbankErstellen(buchung);
        int rechnungId = buchungConnector.getRechnungIdForBuchung(1);
        assertTrue(rechnungId > 0);
    }

    @Test
    void testSaveDeletedBooking() throws IOException, HotelException {
        Person gast2 = new Gast("fofo", "koko", "fofo.koko@web.de", LocalDate.of(1993, 1, 1), "43654346");
        Buchung buchung = new Buchung(gast2, LocalDate.now(), LocalDate.now().plusDays(8), 1);
        buchungConnector.datenbankErstellen(buchung);
        buchungConnector.datenbankLoeschNachId(1);
        String fileName = BuchungConnector.DELETED_BOOKINGS_FILE;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean deletedBookingFound = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("GelÃ¶schte Buchung: ID=" + 1)) {
                    deletedBookingFound = true;
                    break;
                }
            }
            assertTrue(deletedBookingFound);
        }
    }
}