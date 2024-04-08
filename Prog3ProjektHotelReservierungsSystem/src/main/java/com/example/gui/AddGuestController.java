package com.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.example.prog3projekthotelreservierungssystem.*;
import com.example.prog3projekthotelreservierungssystem.Hotel;
import javafx.stage.Stage;

import java.io.IOException;

public class AddGuestController {


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
    private GuestsController guestsController;
    @FXML
    private Label errorLabel;

    @FXML
    void onClickAddGuest(ActionEvent event) throws HotelException {
        if (lastNameTxtfield.getText().trim().isEmpty() || emailTxtfield.getText().trim().isEmpty()){
            errorLabel.setText("Bitte füllen sie die Felder aus");
            return;
        }

        Person guest = new Gast(firstNameTxtfield.getText(), lastNameTxtfield.getText(), emailTxtfield.getText(), dateChooser.getValue(), telefonNrTxtfield.getText()
        );

        Hotel.gastHinzufuegen(guest);
        guestsController.updateListView(Hotel.getAllGasts());
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setGuestsController(GuestsController guestsController) {
        this.guestsController = guestsController;
    }
}