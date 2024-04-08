package com.example.database;

import com.example.prog3projekthotelreservierungssystem.HotelException;
import com.example.prog3projekthotelreservierungssystem.Zimmer;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;

import java.util.List;

/**
 * Die Klasse ZimmerConnector implementiert die DbOperator-Schnittstelle und ermöglicht Operationen für Zimmer in der Datenbank.
 */

public class ZimmerConnector implements DbOperator {

    /**
     * Methode zum Erstellen eines Zimmers in der Datenbank.
     *
     * @param object Das zu erstellende Zimmer.
     * @throws HotelException Wenn das übergebene Objekt kein Zimmer ist.
     */
    @Override
    public void datenbankErstellen(Object object) throws HotelException {
        if (!(object instanceof Zimmer)) {
            throw new HotelException("Das Objekt ist kein Zimmer");
        }
        Session session = JDBCConnector.getSession();
        Zimmer zimmer = (Zimmer) object;
        session.getTransaction().begin();
        session.persist(zimmer);
        session.getTransaction().commit();
    }

    /**
     * Methode zum Abrufen aller Zimmer aus der Datenbank.
     *
     * @return Eine Liste aller Zimmer in der Datenbank.
     */
    @Override
    public List<Zimmer> datenbankSuchAlles() {
        List<Zimmer> allzimmer = null;
        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();
            String queryString = "FROM Zimmer";
            TypedQuery<Zimmer> query = session.createQuery(queryString, Zimmer.class);
            allzimmer = query.getResultList();
            session.getTransaction().commit();
            return allzimmer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allzimmer;
    }

    /**
     * Methode zum Suchen eines Zimmers anhand seiner ID in der Datenbank.
     *
     * @param id Die ID des gesuchten Zimmers.
     * @return Das gefundene Zimmer oder null, wenn kein Zimmer mit dieser ID gefunden wurde.
     */
    @Override
    public <T> T datenbankSuchNachId(int id) {
        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();
            Zimmer zimmer = session.find(Zimmer.class, id);
            session.getTransaction().commit();
            return (T) zimmer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Methode zum Löschen aller Zimmer aus der Datenbank (wird noch implementiert).
     */
    @Override
    public void datenbankLoeschAlles() {

        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();
            //wird noch implementiert
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode zum Löschen eines Zimmers anhand seiner ID aus der Datenbank (wird noch implementiert).
     *
     * @param id Die ID des zu löschenden Zimmers.
     */
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

    /**
     * Methode zum Aktualisieren eines Zimmers in der Datenbank.
     *
     * @param object Das zu aktualisierende Zimmer.
     */
    @Override
    public void datenbankAktualisieren(Object object) {


        try (Session session = JDBCConnector.getSession()) {
            Zimmer zimmer = (Zimmer) object;
            session.getTransaction().begin();
            session.merge(zimmer);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}