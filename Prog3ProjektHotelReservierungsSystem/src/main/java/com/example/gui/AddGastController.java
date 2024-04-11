package com.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.example.prog3projekthotelreservierungssystem.*;
import com.example.prog3projekthotelreservierungssystem.Hotel;
import javafx.stage.Stage;

public class AddGastController {


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
    private Stage stage;
    private GaesteController gaesteController;
    @FXML
    private Label errorLabel;

    @FXML
    void onClickAddGuest(ActionEvent event) throws HotelException {
        if (lastNameTxtfield.getText().trim().isEmpty() || emailTxtfield.getText().trim().isEmpty()
                || firstNameTxtfield.getText().trim().isEmpty() || telefonNrTxtfield.getText().trim().isEmpty()
                || dateChooser.getValue() == null) {
            errorLabel.setText("Bitte füllen sie die Felder aus");
            return;
        }
        if (!firstNameTxtfield.getText().matches("[a-zA-Z]+") || !lastNameTxtfield.getText().matches("[a-zA-Z]+")) {
            errorLabel.setText("Vorname und Name dürfen nur Buchstaben enthalten");
            return;
        }
        if (!telefonNrTxtfield.getText().matches("[0-9]+")){
            errorLabel.setText("TelefonNr darf nur Zahlen enthalten");
            return;
        }

        Person guest = new Gast(firstNameTxtfield.getText(), lastNameTxtfield.getText(), emailTxtfield.getText(), dateChooser.getValue(), telefonNrTxtfield.getText()
        );

        Hotel.gastHinzufuegen(guest);
        gaesteController.updateListView(Hotel.getAllGasts());
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setGuestsController(GaesteController gaesteController) {
        this.gaesteController = gaesteController;
    }
}