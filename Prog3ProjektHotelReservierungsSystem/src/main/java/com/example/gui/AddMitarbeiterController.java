package com.example.gui;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import com.example.database.JDBCConnector;
import com.example.prog3projekthotelreservierungssystem.HotelException;
import com.example.prog3projekthotelreservierungssystem.Mitarbeiter;
import com.example.prog3projekthotelreservierungssystem.Validator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddMitarbeiterController {

    @FXML
    private Button addMitarbeiterBtn;

    @FXML
    private DatePicker dateChooser;

    @FXML
    private TextField emailTxtfield;

    @FXML
    private TextField firstNameTxtfield;

    @FXML
    private TextField lastNameTxtfield;

    @FXML
    private TextField telefonNrTxtfield;

    @FXML
    void onClickAddMitarbeiter(ActionEvent event) throws HotelException {
        String firstName = firstNameTxtfield.getText().trim();
        String lastName = lastNameTxtfield.getText().trim();
        String email = emailTxtfield.getText().trim();
        String telefonNr = telefonNrTxtfield.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || telefonNr.isEmpty() || dateChooser.getValue() == null) {
            Validator.check(false, "Bitte alle Textfelder ausfüllen");
        } else {
            LocalDate geburtsdatum = dateChooser.getValue();
            try {
                addMitarbeiterToDatabase(firstName, lastName, email, geburtsdatum, telefonNr);

            } catch (HotelException e) {
                e.printStackTrace();

            }
        }
    }

    private void addMitarbeiterToDatabase(String firstName, String lastName, String email, LocalDate geburtsdatum, String telefonNr) throws HotelException {
        try {
            Connection connection = JDBCConnector.getConnection();
            EntityManager entityManager = JDBCConnector.getEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            // Überprüfen, ob der Mitarbeiter bereits existiert
            Mitarbeiter existingMitarbeiter = entityManager.find(Mitarbeiter.class, email);
            if (existingMitarbeiter != null) {
                Validator.check(false, "Mitarbeiter existiert bereits");
            }

            // Mitarbeiter hinzufügen, falls er nicht existiert
            Mitarbeiter newMitarbeiter = new Mitarbeiter(firstName, lastName, email, geburtsdatum, telefonNr);
            entityManager.persist(newMitarbeiter);

            transaction.commit();
            entityManager.close();
            JDBCConnector.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HotelException("Fehler beim Hinzufügen des Mitarbeiters zur Datenbank");
        }
    }
}
