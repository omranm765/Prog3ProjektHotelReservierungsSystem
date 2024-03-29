package com.example.database;

import com.example.prog3projekthotelreservierungssystem.Buchung;
import com.example.prog3projekthotelreservierungssystem.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class BuchungConnector implements DbOperator{

    @Override
    public void datenbankErstellen(Object object)
    {

        EntityManager manager = JDBCConnector.getEntityManager();
        Buchung buchung = (Buchung) object;
        manager.getTransaction().begin();
        manager.persist(buchung);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public List<Buchung> datenbankSuchAlles()
    {
        EntityManager manager = JDBCConnector.getEntityManager();
        manager.getTransaction().begin();

        String queryString = "SELECT * FROM Buchung";
        TypedQuery<Buchung> query = manager.createQuery(queryString, Buchung.class);
        List<Buchung> allBuchung = query.getResultList();

        manager.getTransaction().commit();
        manager.close();
        return allBuchung;
    }


    @Override
    public <T> T datenbankSuchNachId(int id) {
        try (EntityManager manager = JDBCConnector.getEntityManager()) {
            manager.getTransaction().begin();
            Buchung buchung = manager.find(Buchung.class, id);
            manager.getTransaction().commit();
            return (T) buchung;
        }
    }

    @Override
    public void datenbankLoeschAlles()
    {
        try (EntityManager manager = JDBCConnector.getEntityManager()){
            manager.getTransaction().begin();
            String moveQuery = "INSERT INTO Buchung_trash_collection SELECT * FROM Buchung";
            manager.createNativeQuery(moveQuery).executeUpdate();

            String deleteQuery = "DELETE FROM Buchung";
            manager.createNativeQuery(deleteQuery).executeUpdate();
            manager.getTransaction().commit();

        }
    }

    @Override
    public void datenbankLoeschNachId(int id) {
        EntityManager manager = JDBCConnector.getEntityManager();
        manager.getTransaction().begin();

        manager.createNativeQuery("INSERT INTO Buchung_trash_collection SELECT * FROM Buchung WHERE Buchung_id = :id")  //--> !!
                .setParameter("id", id)
                .executeUpdate();

        manager.createNativeQuery("DELETE FROM Buchung WHERE Buchung_id = :id")
                .setParameter("id", id)
                .executeUpdate();

        manager.getTransaction().commit();
        manager.close();

    }

    @Override
    public void datenbankAktualisieren(Object object) {

        try (EntityManager manager = JDBCConnector.getEntityManager()) {
            Buchung buchung = (Buchung) object;
            manager.getTransaction().begin();
            manager.merge(buchung);
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
