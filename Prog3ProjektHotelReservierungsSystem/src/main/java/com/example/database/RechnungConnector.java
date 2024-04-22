package com.example.database;

import com.example.prog3projekthotelreservierungssystem.HotelException;
import com.example.prog3projekthotelreservierungssystem.Rechnung;
import com.example.prog3projekthotelreservierungssystem.Zimmer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;

import java.util.List;
/**
 * Die Klasse RechnungConnector implementiert die DbOperator-Schnittstelle und ermöglicht Operationen für Rechnungen in der Datenbank.
 */
public class RechnungConnector implements DbOperator {

    /**
     * Methode zum Erstellen einer Rechnung in der Datenbank.
     *
     * @param object Die zu erstellende Rechnung.
     * @throws HotelException Wenn das übergebene Objekt keine Rechnung ist.
     */
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
        session.close();
    }
    /**
     * Methode zum Abrufen aller Rechnungen aus der Datenbank.
     *
     * @return Eine Liste aller Rechnungen in der Datenbank.
     */
    @Override
    public List<Rechnung> datenbankSuchAlles() {
            List<Rechnung> allrechnung = null;
        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();
            String queryString = "FROM Rechnung";
            TypedQuery<Rechnung> query = session.createQuery(queryString, Rechnung.class);
            allrechnung = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return allrechnung;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allrechnung;
    }
    /**
     * Methode zum Suchen einer Rechnung anhand ihrer ID in der Datenbank.
     *
     * @param id Die ID der gesuchten Rechnung.
     * @return Die gefundene Rechnung oder null, wenn keine Rechnung mit dieser ID gefunden wurde.
     */
    @Override
    public <T> T datenbankSuchNachId(int id) throws HotelException {
        try (Session session = JDBCConnector.getSession()) {
            if (id <= 0) {
                throw new HotelException("Die ID muss größer als 0 sein.");
            }
            session.getTransaction().begin();
            Rechnung rechnung = session.find(Rechnung.class, id);
            session.getTransaction().commit();
            return (T) rechnung;
        }
    }
    /**
     * Methode zum Löschen aller Rechnungen aus der Datenbank (wird noch implementiert).
     */
    @Override
    public void datenbankLoeschAlles() {
        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();
            String queryString = "DELETE FROM Rechnung";
            Query query = session.createQuery(queryString, Rechnung.class);
            int geloeschteRechnung = query.executeUpdate();
            System.out.println(geloeschteRechnung + " Rechnung erfolgreich gelöscht.");
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Methode zum Löschen einer Rechnung anhand ihrer ID aus der Datenbank (wird noch implementiert).
     *
     * @param id Die ID der zu löschenden Rechnung.
     */
    @Override
    public void datenbankLoeschNachId(int id) {
        try (Session session = JDBCConnector.getSession()) {
            if (id <= 0) {
                throw new HotelException("Die ID muss größer als 0 sein.");
            }
            session.getTransaction().begin();
            Rechnung rechnung = session.find(Rechnung.class, id);
            if (rechnung != null) {
                session.remove(rechnung);
                System.out.println("Rechnung mit ID " + id + " erfolgreich gelöscht.");
            } else {
                System.out.println("Rechnung mit ID " + id + " nicht gefunden.");
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Methode zum Aktualisieren einer Rechnung in der Datenbank.
     *
     * @param object Die zu aktualisierende Rechnung.
     */
    @Override
    public void datenbankAktualisieren(Object object) {
        try (Session session = JDBCConnector.getSession()) {
            if (object == null){
                throw new HotelException("Rechnung existiert nicht");
            }
            if (!(object instanceof Rechnung)) {
                throw new HotelException("Das Objekt ist kein Rechnung");
            }
            Rechnung rechnung = (Rechnung) object;
            session.getTransaction().begin();
            session.merge(rechnung);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}