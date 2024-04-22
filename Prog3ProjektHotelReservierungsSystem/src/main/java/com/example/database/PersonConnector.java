package com.example.database;


import com.example.prog3projekthotelreservierungssystem.*;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;

import java.util.List;

/**
 * Die Klasse PersonConnector implementiert die DbOperator-Schnittstelle und ermöglicht Operationen für Personen in der Datenbank.
 */
public class PersonConnector implements DbOperator {

    /**
     * Methode zum Erstellen einer Person in der Datenbank.
     *
     * @param object Die zu erstellende Person.
     * @throws HotelException Wenn das übergebene Objekt keine Person ist.
     */
    @Override
    public void datenbankErstellen(Object object) throws HotelException {
        if (!(object instanceof Person)) {
            throw new HotelException("Das Objekt ist kein Person");
        }
        Session session = JDBCConnector.getSession();
        Person person = (Gast) object;
        session.getTransaction().begin();
        session.persist(person);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Methode zum Abrufen aller Personen aus der Datenbank.
     *
     * @return Eine Liste aller Personen in der Datenbank.
     */
    @Override
    public List<Person> datenbankSuchAlles() {
        List<Person> allPerson = null;
        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();
            String queryString = "FROM Person";
            TypedQuery<Person> query = session.createQuery(queryString, Person.class);
            allPerson = query.getResultList();
            session.getTransaction().commit();
            return allPerson;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allPerson;
    }

    /**
     * Methode zum Suchen einer Person anhand ihrer ID in der Datenbank.
     *
     * @param id Die ID der gesuchten Person.
     * @return Die gefundene Person oder null, wenn keine Person mit dieser ID gefunden wurde.
     */
    @Override
    public <T> T datenbankSuchNachId(int id) {
        try (Session session = JDBCConnector.getSession()) {
            if (id <= 0) {
                throw new HotelException("Die ID muss größer als 0 sein.");
            }
            session.getTransaction().begin();
            Person person = session.find(Person.class, id);
            session.getTransaction().commit();
            return (T) person;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sucht eine Person im Datenbank, die die übergebene E-Mail hat
     * @param email Die zu suchende E-Mail
     * @return Die Gefundene Person
     */
    public <T> T datenbankSuchNachEmail(String email) {
        try (Session session = JDBCConnector.getSession()) {
            if (email == null) {
                throw new HotelException("Email darf nicht leer sein.");
            }
            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
                throw new HotelException("Ungültiger Email");
            }
            session.getTransaction().begin();
            Query query = session.createQuery("SELECT p FROM Person p WHERE p.email = :email", Person.class);
            query.setParameter("email", email);
            Person person = (Person) query.getSingleResult();
            session.getTransaction().commit();
            return (T) person;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Methode zum Löschen aller Personen aus der Datenbank (wird noch implementiert).
     */
    @Override
    public void datenbankLoeschAlles() {
        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();
            String queryString = "DELETE FROM Person";
            Query query = session.createQuery(queryString, Person.class);
            int geloeschtePerson = query.executeUpdate();
            System.out.println(geloeschtePerson + " Person erfolgreich gelöscht.");
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode zum Löschen einer Person anhand ihrer ID aus der Datenbank (wird noch implementiert).
     *
     * @param id Die ID der zu löschenden Person.
     */
    @Override
    public void datenbankLoeschNachId(int id) {
        try (Session session = JDBCConnector.getSession()) {
            if (id <= 0) {
                throw new HotelException("Die ID muss größer als 0 sein.");
            }
            session.getTransaction().begin();
            Person person = session.find(Person.class, id);
            if (person != null) {
                session.remove(person);
                System.out.println("Person mit ID " + id + " erfolgreich gelöscht.");
            } else {
                System.out.println("Person mit ID " + id + " nicht gefunden.");
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Methode zum Aktualisieren einer Person in der Datenbank.
     *
     * @param object Die zu aktualisierende Person.
     */
    @Override
    public void datenbankAktualisieren(Object object) {
        try (Session session = JDBCConnector.getSession()) {
            if (object == null){
                throw new HotelException("Person existiert nicht");
            }
            if (!(object instanceof Person)) {
                throw new HotelException("Das Objekt ist kein Person");
            }
            Person person = (Person) object;
            session.getTransaction().begin();
            session.merge(person);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}