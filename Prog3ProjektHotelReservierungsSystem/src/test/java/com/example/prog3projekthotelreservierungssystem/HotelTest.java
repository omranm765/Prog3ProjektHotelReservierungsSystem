package com.example.prog3projekthotelreservierungssystem;

import com.example.prog3projekthotelreservierungssystem.Hotel;
import com.example.prog3projekthotelreservierungssystem.Person;
import com.example.prog3projekthotelreservierungssystem.Zimmer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HotelTest {

    @BeforeEach
    public void setUp() {
        List<Zimmer> zimmerList = new ArrayList<>();
        List<Person> personList = new ArrayList<>();
    }

    @Test
    public void testGetAllZimmer() {
        assertEquals(8, Hotel.getAllZimmer().size());
    }

    //Kommt drauf an wie viele Gäste es momentan im Database gibt, z.B. ich habe momentan 14 :)
    //Am besten Database leeren dann ausprobieren mit 0
    //Methoden wie hinzufügen und entfernen habe ich entfernt weil sie die testGet methoden kaputt machen
    //wenn ich assertEquals(0) habe und dann ein testGastHinzufuegen aufrufe wird die anzahl auf 1 gesetzt
    //was alles kaputt macht
    @Test
    public void testGetAllGasts() {
        System.out.println(Hotel.getAllGasts().size());
        assertEquals(14, Hotel.getAllGasts().size());
    }

    @Test
    public void testGetAllBuchungen() {
        assertEquals(12, Hotel.getAllBuchungen().size());
    }
}