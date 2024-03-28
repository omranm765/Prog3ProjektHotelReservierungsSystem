package com.example.database;


import com.example.prog3projekthotelreservierungssystem.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PersonConnector implements DbOperator {

    @Override
    public void datenbankErstellen(Object object)
    {

        EntityManager manager = JDBCConnector.getEntityManager();
        Person person = (Person) object;
        manager.getTransaction().begin();
        manager.persist(person);
        manager.getTransaction().commit();
        manager.close();
    }

    @Override
    public List<Person> datenbankSuchAlles()
    {
        EntityManager manager = JDBCConnector.getEntityManager();
        manager.getTransaction().begin();

        String queryString = "SELECT * FROM Person";
        TypedQuery<Person> query = manager.createQuery(queryString, Person.class);
        List<Person> allPerson = query.getResultList();

        manager.getTransaction().commit();
        manager.close();
        return allPerson;
    }

    @Override
    public <T> T datenbankSuchNachId(int id) {
        try (EntityManager manager = JDBCConnector.getEntityManager()) {
            manager.getTransaction().begin();
            Person person = manager.find(Person.class, id);
            manager.getTransaction().commit();
            return (T) person;
        }
    }



    @Override
    public void datenbankLoeschAlles()
    {


        try (EntityManager manager = JDBCConnector.getEntityManager()){
        manager.getTransaction().begin();
            String moveQuery = "INSERT INTO Person_trash_collection SELECT * FROM Person";
            manager.createNativeQuery(moveQuery).executeUpdate();

            String deleteQuery = "DELETE FROM Person";
            manager.createNativeQuery(deleteQuery).executeUpdate();
        manager.getTransaction().commit();

        }
    }

    @Override
    public void datenbankLoeschNachId(int id) {
        EntityManager manager = JDBCConnector.getEntityManager();
        manager.getTransaction().begin();

        manager.createNativeQuery("INSERT INTO Person_trash_collection SELECT * FROM Person WHERE Person_id = :id")
                .setParameter("id", id)
                .executeUpdate();

        manager.createNativeQuery("DELETE FROM Person WHERE Person_id = :id")
                .setParameter("id", id)
                .executeUpdate();

        manager.getTransaction().commit();
        manager.close();

        }


    @Override
    public void datenbankAktualisieren(Object object) {

        EntityManager manager = JDBCConnector.getEntityManager();
        Person person = (Person) object;
        try {
            manager.getTransaction().begin();
            manager.merge(person);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive())
                manager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (manager != null && manager.isOpen())
                manager.close();
        }
    }

    }