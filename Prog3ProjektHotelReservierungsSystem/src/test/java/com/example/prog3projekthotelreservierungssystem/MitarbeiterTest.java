package com.example.prog3projekthotelreservierungssystem;


import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;


public class MitarbeiterTest {

    @Test
    public void testMitarbeiterConstructor() throws HotelException {
        Mitarbeiter mitarbeiter = new Mitarbeiter("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234567890");
        assertNotNull(mitarbeiter);
        assertEquals("John", mitarbeiter.getVorname());
        assertEquals("Doe", mitarbeiter.getName());
        assertEquals("john.doe@example.com", mitarbeiter.getEmail());
        assertEquals(LocalDate.of(1990, 1, 1), mitarbeiter.getGeburtsdatum());
        assertEquals("1234567890", mitarbeiter.getTelefonNr());
    }

    @Test
    public void testMitarbeiterEquals() throws HotelException {
        Mitarbeiter mitarbeiter1 = new Mitarbeiter("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234567890");
        Mitarbeiter mitarbeiter2 = new Mitarbeiter("Jane", "Doe", "jane.doe@example.com", LocalDate.of(1995, 2, 2), "0987654321");
        Mitarbeiter mitarbeiter3 = new Mitarbeiter("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234567890");

        assertNotEquals(mitarbeiter1, mitarbeiter2);
        assertEquals(mitarbeiter1, mitarbeiter3);
    }

    @Test
    public void testMitarbeiterHashCode() throws HotelException {
        Mitarbeiter mitarbeiter1 = new Mitarbeiter("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234567890");
        Mitarbeiter mitarbeiter2 = new Mitarbeiter("Jane", "Doe", "jane.doe@example.com", LocalDate.of(1995, 2, 2), "0987654321");
        Mitarbeiter mitarbeiter3 = new Mitarbeiter("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234567890");

        assertNotEquals(mitarbeiter1.hashCode(), mitarbeiter2.hashCode());
        assertEquals(mitarbeiter1.hashCode(), mitarbeiter3.hashCode());
    }

    @Test
    public void testMitarbeiterToString() throws HotelException {
        Mitarbeiter mitarbeiter = new Mitarbeiter("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234567890");
        String expected = "Mitarbeiter\nId: 0\nName: John Doe\nGeburtsdatum: 1990-01-01\nEmail: john.doe@example.com\nTelefonNr: 1234567890\n";
        assertEquals(expected, mitarbeiter.toString());
    }
}