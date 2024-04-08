package com.example.gui;

import java.time.LocalDate;
import com.example.prog3projekthotelreservierungssystem.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddMitarbeiterController {
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
    private Label errorLabel;

    @FXML
    void onClickAddMitarbeiter(ActionEvent event) throws HotelException {
        String firstName = firstNameTxtfield.getText().trim();
        String lastName = lastNameTxtfield.getText().trim();
        String email = emailTxtfield.getText().trim();
        LocalDate geburtsdatum = dateChooser.getValue();
        String telefonNr = telefonNrTxtfield.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || telefonNr.isEmpty() || dateChooser.getValue() == null) {
            errorLabel.setText("Bitte alle Textfelder ausfüllen");
        }
        Person mitarbeiter = new Mitarbeiter(firstName, lastName, email, geburtsdatum,
                telefonNr);
        Hotel.mitarbeiterHinzufuegen(mitarbeiter);
    }
}
