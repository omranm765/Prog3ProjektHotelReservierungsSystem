package com.example.database;


import com.example.prog3projekthotelreservierungssystem.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;

import java.util.List;

public class PersonConnector implements DbOperator {

    @Override
    public void datenbankErstellen(Object object) throws HotelException {
        if(!(object instanceof Person)){
            throw new HotelException("Das Objekt ist kein Person");
        }
        Session session = JDBCConnector.getSession();
            Person person = (Gast) object;
            session.getTransaction().begin();
            session.persist(person);
            session.getTransaction().commit();
    }

    @Override
    public List<Person> datenbankSuchAlles() {
        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();
            String queryString = "SELECT * FROM Person";
            TypedQuery<Person> query = session.createQuery(queryString, Person.class);
            List<Person> allPerson = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return allPerson;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T datenbankSuchNachId(int id) {
        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();
            Person person = session.find(Person.class, id);
            session.getTransaction().commit();
            session.close();
            return (T) person;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;//TODO niemals null zurück KEINE UMLAUTE
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
            //Hier wollten wir eine frage stellen und zwar dürfen wir ein Person löschen wenn er Buchung storniert?
            //oder müssen wir eine extra tabelle für Personen machen die wir "Löschen" bzw in die andere tabelle rein machen
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void datenbankAktualisieren(Object object) {

        try (Session session = JDBCConnector.getSession()) {
            Person person = (Person) object;
            session.getTransaction().begin();
            session.merge(person);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}