package com.example.database;

import com.example.prog3projekthotelreservierungssystem.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonConnectorTest {

    private PersonConnector personConnector;

    @BeforeEach
    void setUp() {
        personConnector = new PersonConnector();
    }

    @AfterEach
    void tearDown() {
        personConnector.datenbankLoeschAlles();
    }

    @Test
    void testDatenbankErstellen() throws HotelException {
        Person person = new Gast("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234567890");
        assertDoesNotThrow(() -> personConnector.datenbankErstellen(person));
        Person foundPerson = personConnector.datenbankSuchNachId(1);
        assertEquals(person, foundPerson);
    }

    @Test
    void testDatenbankSuchAlles() throws HotelException {
        Person person1 = new Gast("Jane", "Doe", "jane.doe@example.com", LocalDate.of(1995, 2, 2), "0987654321");
        Person person2 = new Gast("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234567890");
        personConnector.datenbankErstellen(person1);
        personConnector.datenbankErstellen(person2);
        List<Person> allPersons = personConnector.datenbankSuchAlles();
        assertTrue(allPersons.contains(person1));
        assertTrue(allPersons.contains(person2));
    }

    @Test
    void testDatenbankSuchNachId() throws HotelException {
        Person person = new Gast("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234567890");
        personConnector.datenbankErstellen(person);
        Person foundPerson = personConnector.datenbankSuchNachId(1);
        assertEquals(person, foundPerson);
    }

    @Test
    void testDatenbankLoeschAlles() throws HotelException {
        Person person1 = new Gast("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234567890");
        Person person2 = new Gast("Jane", "Doe", "jane.doe@example.com", LocalDate.of(1995, 2, 2), "0987654321");
        personConnector.datenbankErstellen(person1);
        personConnector.datenbankErstellen(person2);
        personConnector.datenbankLoeschAlles();
        List<Person> allPersons = personConnector.datenbankSuchAlles();
        assertTrue(allPersons.isEmpty());
    }

    @Test
    void testDatenbankLoeschNachId() throws HotelException {
        Person person = new Gast("Jane", "Doe", "jane.doe@example.com", LocalDate.of(1995, 2, 2), "0987654321");
        personConnector.datenbankErstellen(person);
        personConnector.datenbankLoeschNachId(1);
        List<Person> allPersons = personConnector.datenbankSuchAlles();
        assertTrue(allPersons.isEmpty());
    }

    @Test
    void testDatenbankAktualisieren() throws HotelException {
        Person person = new Gast("Jane", "Doe", "jane.doe@example.com", LocalDate.of(1995, 2, 2), "0987654321");
        personConnector.datenbankErstellen(person);
        person.setEmail("<EMAIL>");
        personConnector.datenbankAktualisieren(person);
        Person foundPerson = personConnector.datenbankSuchNachId(1);
        assertEquals("<EMAIL>", foundPerson.getEmail());
    }
}