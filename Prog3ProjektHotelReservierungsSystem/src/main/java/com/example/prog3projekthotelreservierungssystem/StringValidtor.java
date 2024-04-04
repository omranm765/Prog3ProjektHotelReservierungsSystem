package com.example.prog3projekthotelreservierungssystem;

public class StringValidtor {
    public StringValidtor(){

    }

    public static boolean stringCheckNullOrEmpty(String statement){
        return statement == null || statement.trim().isEmpty();
    }

    public static boolean stringCheckREGEX(String statement, String regex){
        return !statement.matches(regex);
    }
}
