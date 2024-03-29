package com.example.database;


import com.example.prog3projekthotelreservierungssystem.Gast;
import com.example.prog3projekthotelreservierungssystem.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PersonConnector implements DbOperator {

    @Override
    public void datenbankErstellen(Object object)
    {

        EntityManager manager = JDBCConnector.getEntityManager();
        Person person = (Gast) object;
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
            //wird noch implementiert
            manager.getTransaction().commit();

        }
    }

    @Override
    public void datenbankLoeschNachId(int id) {
        EntityManager manager = JDBCConnector.getEntityManager();
        manager.getTransaction().begin();

        //wird noch implementiert

        manager.getTransaction().commit();
        manager.close();

    }


    @Override
    public void datenbankAktualisieren(Object object) {

        try (EntityManager manager = JDBCConnector.getEntityManager()) {
            Person person = (Person) object;
            manager.getTransaction().begin();
            manager.merge(person);
            manager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}