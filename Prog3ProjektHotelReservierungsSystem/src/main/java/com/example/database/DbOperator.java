package com.example.database;

import java.util.List;

public interface DbOperator {

    public void datenbankErstellen (Object object);
    public List<?> datenbankSuchAlles();
    public <T> T datenbankSuchNachId  (int id);

    public void datenbankLoeschAlles();
    public void datenbankLoeschNachId(int id);

    public void datenbankAktualisieren(Object object);

}
