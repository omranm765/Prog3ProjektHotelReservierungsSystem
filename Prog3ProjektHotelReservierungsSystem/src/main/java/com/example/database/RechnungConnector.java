package com.example.database;

import com.example.prog3projekthotelreservierungssystem.HotelException;
import com.example.prog3projekthotelreservierungssystem.Rechnung;
import com.example.prog3projekthotelreservierungssystem.Zimmer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;

import java.util.List;

public class RechnungConnector implements DbOperator {


    @Override
    public void datenbankErstellen(Object object) throws HotelException {
        if(!(object instanceof Rechnung)){
            throw new HotelException("Das Objekt ist kein Rechnung");
        }
        Session session = JDBCConnector.getSession();
        Rechnung rechnung = (Rechnung) object;
        session.getTransaction().begin();
        session.persist(rechnung);
        session.getTransaction().commit();
    }

    @Override
    public List<?> datenbankSuchAlles() {

        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();

            String queryString = "SELECT * FROM Rechnung";
            TypedQuery<Rechnung> query = session.createQuery(queryString, Rechnung.class);
            List<Rechnung> allrechnung = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return allrechnung;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T datenbankSuchNachId(int id) {
        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();
            Rechnung rechnung = session.find(Rechnung.class, id);
            session.getTransaction().commit();
            return (T) rechnung;
        }
    }

    @Override
    public void datenbankLoeschAlles() {
        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();
            // wird noch implementiert
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void datenbankLoeschNachId(int id) {

        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();
            //wird noch implementiert
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void datenbankAktualisieren(Object object) {
        try (Session session = JDBCConnector.getSession()) {
            Rechnung rechnung = (Rechnung) object;
            session.getTransaction().begin();
            session.merge(rechnung);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}