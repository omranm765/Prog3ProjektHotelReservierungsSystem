package com.example.database;

import com.example.prog3projekthotelreservierungssystem.HotelException;
import com.example.prog3projekthotelreservierungssystem.Zimmer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;

import java.util.List;


public class ZimmerConnector implements DbOperator {


    @Override
    public void datenbankErstellen(Object object) throws HotelException {
        if(!(object instanceof Zimmer)){
            throw new HotelException("Das Objekt ist kein Zimmer");
        }
        Session session = JDBCConnector.getSession();
            Zimmer zimmer = (Zimmer) object;
            session.getTransaction().begin();
            session.persist(zimmer);
            session.getTransaction().commit();
    }

    @Override
    public List<?> datenbankSuchAlles() {

        try (Session session = JDBCConnector.getSession()) {
            session.getTransaction().begin();

            String queryString = "SELECT * FROM Zimmer";
            TypedQuery<Zimmer> query = session.createQuery(queryString, Zimmer.class);
            List<Zimmer> allzimmer = query.getResultList();
            session.getTransaction().commit();
            session.close();
            return allzimmer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
            Zimmer zimmer = (Zimmer) object;
            session.getTransaction().begin();
            session.merge(zimmer);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}