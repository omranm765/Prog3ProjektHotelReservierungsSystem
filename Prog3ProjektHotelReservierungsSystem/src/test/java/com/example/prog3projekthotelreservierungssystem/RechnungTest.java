package com.example.prog3projekthotelreservierungssystem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class RechnungTest {

    @Test
    public void testRechnung() {
        try {
            Rechnung rechnung = new Rechnung(10.0, LocalDate.now(), Rechnung.Status.BEZAHLT);
            assertEquals(10.0, rechnung.getPreis());
            assertNotNull(rechnung.getErstellungsDatum());
            assertEquals(Rechnung.Status.BEZAHLT, rechnung.getStatus());
        } catch (HotelException e) {
            fail("Did not expect HotelException to be thrown");
        }
    }

    @Test
    public void testRechnungWithInvalidPrice() {
        assertThrows(HotelException.class, () -> {
            new Rechnung(0.0, LocalDate.now(), Rechnung.Status.BEZAHLT);
        });
    }

    @Test
    public void testRechnungWithNullErstellungsDatum() {
        assertThrows(HotelException.class, () -> {
            new Rechnung(10.0, null, Rechnung.Status.BEZAHLT);
        });
    }

    @Test
    public void testRechnungEquals() throws HotelException {
        Rechnung rechnung1 = new Rechnung(10.0, LocalDate.now(), Rechnung.Status.BEZAHLT);
        Rechnung rechnung2 = new Rechnung(10.0, LocalDate.now(), Rechnung.Status.BEZAHLT);
        Rechnung rechnung3 = new Rechnung(20.0, LocalDate.now(), Rechnung.Status.BEZAHLT);

        assertEquals(rechnung1, rechnung2);
        assertNotEquals(rechnung1, rechnung3);
        assertNotEquals(rechnung1, null);
        assertNotEquals(rechnung1, new Object());
    }

    @Test
    public void testRechnungHashCode() throws HotelException {
        Rechnung rechnung1 = new Rechnung(10.0, LocalDate.now(), Rechnung.Status.BEZAHLT);
        Rechnung rechnung2 = new Rechnung(10.0, LocalDate.now(), Rechnung.Status.BEZAHLT);
        Rechnung rechnung3 = new Rechnung(20.0, LocalDate.now(), Rechnung.Status.BEZAHLT);

        assertEquals(rechnung1.hashCode(), rechnung2.hashCode());
        assertNotEquals(rechnung1.hashCode(), rechnung3.hashCode());
    }
}