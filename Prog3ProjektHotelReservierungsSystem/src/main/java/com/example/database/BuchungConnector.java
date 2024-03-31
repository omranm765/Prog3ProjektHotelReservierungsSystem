package com.example.database;

import com.example.prog3projekthotelreservierungssystem.Buchung;
import com.example.prog3projekthotelreservierungssystem.Gast;
import com.example.prog3projekthotelreservierungssystem.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;

import java.util.List;

public class BuchungConnector implements DbOperator {

    @Override
    public void datenbankErstellen(Object object) {
        Session session = JDBCConnector.getSession();
        Buchung buchung = (Buchung) object;
            session.getTransaction().begin();
            session.persist(buchung);
            session.getTransaction().commit();
    }

    @Override
    public List<Buchung> datenbankSuchAlles() {
        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();
            String queryString = "SELECT * FROM Person";
            TypedQuery<Buchung> query = session.createQuery(queryString, Buchung.class);
            List<Buchung> allBuchung = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return allBuchung;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public <T> T datenbankSuchNachId(int id) {
        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();
            Buchung buchung = session.find(Buchung.class, id);
            session.getTransaction().commit();
            session.close();
            return (T) buchung;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
            // wird noch implementiert
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void datenbankAktualisieren(Object object) {

        try (Session session = JDBCConnector.getSession()) {
            Buchung buchung = (Buchung) object;
            session.getTransaction().begin();
            session.merge(buchung);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
