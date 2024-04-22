package com.example.database;

import com.example.prog3projekthotelreservierungssystem.HotelException;

import java.util.List;
/**
 * Das DbOperator-Interface definiert Methoden zum Interagieren mit der Datenbank.
 */
public interface DbOperator {


    public void datenbankErstellen (Object object) throws HotelException;

    public List<?> datenbankSuchAlles();
    public <T> T datenbankSuchNachId  (int id) throws HotelException;

    public void datenbankLoeschAlles();
    public void datenbankLoeschNachId(int id);

    public void datenbankAktualisieren(Object object);
}
