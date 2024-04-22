package com.example.database;

import com.example.prog3projekthotelreservierungssystem.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Die Klasse JDBCConnector stellt eine Verbindung zur Datenbank her und verwaltet Entity Manager und Sessions.
 */
public class JDBCConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/hotel";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static EntityManager entityManager;
    private static SessionFactory sessionFactory;
    private static final String PERSISTENCE = "hotel";
    //Wir hatten Probleme mit den Persistence.xml deswegen kein Entity-manager mehr, sondern Session
    private static EntityManagerFactory entityManagerFactory;
    private static Session session;

    // Statischer Initialisierungsblock für die Initialisierung der SessionFactory, Session, usw.
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


    /**
     * Methode zum Herstellen der Verbindung zur Datenbank.
     *
     * @return Die hergestellte Verbindung zur Datenbank.
     * @throws SQLException Wenn ein Fehler beim Herstellen der Verbindung auftritt.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException("Fehler beim Herstellen der Verbindung zur Datenbank.");
        }
    }


    /**
     * Methode zum Schließen einer Verbindung zur Datenbank.
     *
     * @param connection Die zu schließende Verbindung.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Methode zum Erhalten einer Hibernate-Session.
     *
     * @return Die Hibernate-Session.
     */
    public static Session getSession(){
        if(session == null || !session.isOpen()){
            session = sessionFactory.openSession();
        }
        return session;
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }


    /**
     * Methode zum Erhalten des Entity Managers.
     *
     * @return Der Entity Manager.
     */
    public static EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = sessionFactory.createEntityManager();
        }
        return entityManager;
    }
    /**
     * Methode zum Erhalten des Entity Manager Factory.
     *
     * @return Die Entity Manager Factory.
     */

    public static EntityManagerFactory getEntityManagerFactory(){
        if (entityManagerFactory == null){
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE);
        }
        return entityManagerFactory;
    }

    /**
     * Methode zum Schließen des Entity Managers.
     */
    public static void closeEntityManager() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
    /**
     * Methode zum Schließen der Hibernate-Session.
     */
    public static void closeSession(){
        if(session != null && session.isOpen()){
            session.close();
        }
    }
}
