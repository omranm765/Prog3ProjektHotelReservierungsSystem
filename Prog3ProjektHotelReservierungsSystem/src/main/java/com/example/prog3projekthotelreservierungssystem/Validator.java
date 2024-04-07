package com.example.prog3projekthotelreservierungssystem;

/**
 * Eine Utility-Klasse für die Validierung von Aussagen.
 */
public class Validator {

    public Validator(){

    }

    /**
     * Überprüft eine Aussage und wirft eine HotelException mit der angegebenen Nachricht,
     * falls die Aussage wahr ist.
     *
     * @param statement Die zu überprüfende Aussage.
     * @param message   Die Fehlermeldung für den Fall, dass die Aussage wahr ist.
     * @throws HotelException wenn die Aussage wahr ist.
     */
    public static void check(boolean statement, String message) throws HotelException {
        if (statement){
            throw new HotelException(message);
        }
    }
}
