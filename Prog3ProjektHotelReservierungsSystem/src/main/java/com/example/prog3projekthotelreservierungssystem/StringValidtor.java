package com.example.prog3projekthotelreservierungssystem;

/**
 * Eine Utility-Klasse für die Validierung von Strings.
 */
public class StringValidtor {
    public StringValidtor(){

    }
    /**
     * Überprüft, ob ein String null oder leer ist.
     *
     * @param statement Der zu überprüfende String.
     * @return true, wenn der String null oder leer ist, ansonsten false.
     */
    public static boolean stringCheckNullOrEmpty(String statement){
        return statement == null || statement.trim().isEmpty();
    }
    /**
     * Überprüft, ob ein String einem gegebenen regulären Ausdruck entspricht.
     *
     * @param statement Der zu überprüfende String.
     * @param regex     Der reguläre Ausdruck.
     * @return true, wenn der String dem regulären Ausdruck nicht entspricht, ansonsten false.
     */
    public static boolean stringCheckREGEX(String statement, String regex){
        return !statement.matches(regex);
    }
}

