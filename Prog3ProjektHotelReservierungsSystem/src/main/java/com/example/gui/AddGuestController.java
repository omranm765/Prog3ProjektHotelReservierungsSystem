package com.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import com.example.prog3projekthotelreservierungssystem.*;
import com.example.prog3projekthotelreservierungssystem.Hotel;



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

    @FXML
    void onClickAddGuest(ActionEvent event) throws Exception
    {
        Validator.check(lastNameTxtfield.getText().trim().isEmpty() || emailTxtfield.getText().trim().isEmpty()
                , "Bitte füllen sie die Felder aus");

        Person guest = new Gast(firstNameTxtfield.getText(), lastNameTxtfield.getText(), emailTxtfield.getText(),dateChooser.getValue(), telefonNrTxtfield.getText()
        );

        //add guest to database
        Hotel.gastHinzufuegen(guest);
    }


}