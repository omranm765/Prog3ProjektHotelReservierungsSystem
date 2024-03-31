package com.example.database;

import com.example.prog3projekthotelreservierungssystem.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.hibernate.service.StandardServiceInitiators;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/hotel";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static EntityManager entityManager;
    private static SessionFactory sessionFactory;
    private static final String PERSISTENCE = "hotel";
    private static EntityManagerFactory entityManagerFactory;
    private static Session session;

    // Statischer Initialisierungsblock für die Initialisierung der SessionFactory
    static {
        Configuration configuration = new Configuration().configure().addAnnotatedClass(Gast.class)
                .addAnnotatedClass(Buchung.class)
                .addAnnotatedClass(Rechnung.class)
                .addAnnotatedClass(Zimmer.class)
                .addAnnotatedClass(Mitarbeiter.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        session = sessionFactory.openSession();
    }

    // Methode zum Herstellen der Verbindung zur Datenbank
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException("Fehler beim Herstellen der Verbindung zur Datenbank.");
        }
    }

    // Methode zum Schließen der Verbindung zur Datenbank
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Session getSession(){
        return session;
    }
    // Methode zum Erhalten des Entity Managers
    public static EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = sessionFactory.createEntityManager();
        }
        return entityManager;
    }

    public static EntityManagerFactory getEntityManagerFactory(){
        if (entityManagerFactory == null){
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE);
        }
        return entityManagerFactory;
    }

    // Methode zum Schließen des Entity Managers
    public static void closeEntityManager() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
    public static void closeSession(){
        if(session != null && session.isOpen()){
            session.close();
        }
    }
}
