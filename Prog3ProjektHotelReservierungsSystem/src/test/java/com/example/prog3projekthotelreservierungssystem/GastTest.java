package com.example.prog3projekthotelreservierungssystem;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class GastTest {

    @Test
    public void testGastConstructor() throws HotelException {
        Gast gast = new Gast("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234567890");
        assertNotNull(gast);
        assertEquals("John", gast.getVorname());
        assertEquals("Doe", gast.getName());
        assertEquals("john.doe@example.com", gast.getEmail());
        assertEquals(LocalDate.of(1990, 1, 1), gast.getGeburtsdatum());
        assertEquals("1234567890", gast.getTelefonNr());
    }

    @Test
    public void testGastEquals() throws HotelException {
        Gast gast1 = new Gast("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234567890");
        Gast gast2 = new Gast("Jane", "Doe", "jane.doe@example.com", LocalDate.of(1995, 2, 2), "0987654321");
        Gast gast3 = new Gast("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234567890");

        assertNotEquals(gast1, gast2);
        assertEquals(gast1, gast3);
    }

    @Test
    public void testGastHashCode() throws HotelException {
        Gast gast1 = new Gast("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234567890");
        Gast gast2 = new Gast("Jane", "Doe", "jane.doe@example.com", LocalDate.of(1995, 2, 2), "0987654321");
        Gast gast3 = new Gast("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234567890");

        assertNotEquals(gast1.hashCode(), gast2.hashCode());
        assertEquals(gast1.hashCode(), gast3.hashCode());
    }

    @Test
    public void testGastToString() throws HotelException {
        Gast gast = new Gast("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234567890");
        String expected = "Gast\nId: 0\nName: John Doe\nGeburtsdatum: 1990-01-01\nEmail: john.doe@example.com\nTelefonNr: 1234567890\n";
        assertEquals(expected, gast.toString());
    }
}