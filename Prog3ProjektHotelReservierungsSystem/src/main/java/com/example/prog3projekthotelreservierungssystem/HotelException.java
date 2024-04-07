package com.example.prog3projekthotelreservierungssystem;
/**
 * Eine benutzerdefinierte Ausnahme für Hotel-spezifische Fehler.
 */
public class HotelException extends Exception{

    /**
     * Konstruktor für eine HotelException ohne Nachricht.
     */
    public HotelException(){
        super();
    }
    /**
     * Konstruktor für eine HotelException mit einer bestimmten Nachricht.
     *
     * @param message Die Fehlermeldung, die die Ausnahme beschreibt.
     */
    public HotelException(String message){
        super(message);
    }
}
