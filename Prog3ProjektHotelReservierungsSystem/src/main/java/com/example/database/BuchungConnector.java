package com.example.database;

import com.example.prog3projekthotelreservierungssystem.*;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Stellt Methoden bereit, um mit Buchungsdaten in der Datenbank zu interagieren.
 */
public class BuchungConnector implements DbOperator {

    public static final String DELETED_BOOKINGS_FILE = "deletedBookings.txt";

    /**
     * Fügt eine Buchung der Datenbank hinzu.
     *
     * @param object Das Buchungsobjekt, das hinzugefügt werden soll.
     * @throws HotelException Wenn das übergebene Objekt keine Buchung ist.
     */
    @Override
    public void datenbankErstellen(Object object) throws HotelException {
        if (object == null) {
            throw new HotelException("Buchung existiert nicht");
        }
        if (!(object instanceof Buchung)) {
            throw new HotelException("Das Objekt ist kein Buchung");
        }
        Session session = JDBCConnector.getSession();
        Buchung buchung = (Buchung) object;
        session.getTransaction().begin();
        session.persist(buchung);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Sucht und gibt alle Buchungen aus der Datenbank zurück.
     *
     * @return Eine Liste von Buchungsobjekten aus der Datenbank.
     */
    @Override
    public List<Buchung> datenbankSuchAlles() {
        List<Buchung> allBuchung = null;
        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();
            String queryString = "FROM Buchung";
            TypedQuery<Buchung> query = session.createQuery(queryString, Buchung.class);
            allBuchung = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return allBuchung;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allBuchung;
    }

    /**
     * Sucht nach einer Buchung anhand der ID in der Datenbank.
     *
     * @param id Die ID der gesuchten Buchung.
     * @return Das Buchungsobjekt mit der angegebenen ID.
     */
    @Override
    public <T> T datenbankSuchNachId(int id) {
        try (Session session = JDBCConnector.getSession()) {
            if (id <= 0) {
                throw new HotelException("Die ID muss größer als 0 sein.");
            }
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

    /**
     * Löscht alle Buchungsdaten aus der Datenbank.
     */
    @Override
    public void datenbankLoeschAlles() {
        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();
            String queryString = "DELETE FROM Zimmer";
            Query query = session.createQuery(queryString, Buchung.class);
            int geloeschteBuchung = query.executeUpdate();
            System.out.println(geloeschteBuchung + " Buchung erfolgreich gelöscht");
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Löscht eine Buchung anhand der ID aus der Datenbank.
     *
     * @param id Die ID der zu löschenden Buchung.
     */

    @Override
    public void datenbankLoeschNachId(int id) {
        try (Session session = JDBCConnector.getSession()) {
            if (id <= 0) {
                throw new HotelException("Die ID muss größer als 0 sein.");
            }
            session.getTransaction().begin();
            Buchung buchung = session.find(Buchung.class, id);
            if (buchung != null) {
                saveDeletedBooking(buchung);
                session.remove(buchung);
                System.out.println("Buchung mit ID " + id + " erfolgreich gelöscht.");
            } else {
                System.out.println("Buchung mit ID " + id + " nicht gefunden.");
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Aktualisiert eine Buchung in der Datenbank.
     *
     * @param object Das aktualisierte Buchungsobjekt.
     */

    @Override
    public void datenbankAktualisieren(Object object) {
        try (Session session = JDBCConnector.getSession()) {
            if (object == null) {
                throw new HotelException("Buchung existiert nicht");
            }
            if (!(object instanceof Buchung)) {
                throw new HotelException("Das Objekt ist kein Buchung");
            }
            Buchung buchung = (Buchung) object;
            session.getTransaction().begin();
            session.merge(buchung);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param buchungId Das id zum suchen
     * @return Das gefundene id
     */
    public int getRechnungIdForBuchung(int buchungId) {
        try (Session session = JDBCConnector.getSession()) {
            if (buchungId <= 0) {
                throw new HotelException("Die ID muss größer als 0 sein.");
            }
            session.getTransaction().begin();
            String queryString = "SELECT b.rechnung.rechnungsID FROM Buchung b WHERE b.BuchungID = :buchungId";
            Query query = session.createQuery(queryString, Buchung.class);
            query.setParameter("buchungId", buchungId);
            int rechnungId = (Integer) query.getSingleResult();
            session.getTransaction().commit();
            return rechnungId;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Speichert gelöschte Buchungen in eine Datei damit die Informationen
     * nicht verloren gehen
     * @param buchung Die Buchung, die in der Datei gespeichert werden muss
     */
    private void saveDeletedBooking(Buchung buchung) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DELETED_BOOKINGS_FILE, true))) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            writer.write("Gelöschte Buchung: ID=" + buchung.getBuchungID() + ", Zeitpunkt=" + formattedDateTime);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
