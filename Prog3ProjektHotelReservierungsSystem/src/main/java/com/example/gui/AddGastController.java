package com.example.gui;

import com.example.database.PersonConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.example.prog3projekthotelreservierungssystem.*;
import com.example.prog3projekthotelreservierungssystem.Hotel;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class AddGastController {


    @FXML
    private DatePicker birthdayDateChooser;

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
    void initialize(){
        firstNameTxtfield.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    onClickAddGuest(new ActionEvent());
                } catch (HotelException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        lastNameTxtfield.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    onClickAddGuest(new ActionEvent());
                } catch (HotelException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        emailTxtfield.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    onClickAddGuest(new ActionEvent());
                } catch (HotelException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        birthdayDateChooser.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    onClickAddGuest(new ActionEvent());
                } catch (HotelException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        telefonNrTxtfield.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    onClickAddGuest(new ActionEvent());
                } catch (HotelException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    void onClickAddGuest(ActionEvent event) throws HotelException {
        if (lastNameTxtfield.getText().trim().isEmpty() || emailTxtfield.getText().trim().isEmpty()
                || firstNameTxtfield.getText().trim().isEmpty() || telefonNrTxtfield.getText().trim().isEmpty()
                || birthdayDateChooser.getValue() == null) {
            errorLabel.setText("Bitte füllen sie die Felder aus");
            return;
        }
        if (!firstNameTxtfield.getText().matches("[a-zA-Z ]+") || !lastNameTxtfield.getText().matches("[a-zA-Z ]+")) {
            errorLabel.setText("Vorname und Name dürfen nur Buchstaben enthalten");
            return;
        }
        if (!telefonNrTxtfield.getText().matches("[0-9]+")){
            errorLabel.setText("TelefonNr darf nur Zahlen enthalten");
            return;
        }
        if (!emailTxtfield.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            errorLabel.setText("Ungültiger Email");
            return;
        }
        if (!telefonNrTxtfield.getText().matches("[0-9]+")){
            errorLabel.setText("TelefonNr darf nur Zahlen enthalten");
        }
        if (birthdayDateChooser.getValue().isAfter(LocalDate.now())){
            errorLabel.setText("Ungültige Eingabe!");
            return;
        }
        LocalDate date1 = birthdayDateChooser.getValue();
        LocalDate date2 = LocalDate.now();

        long diffInDays = ChronoUnit.YEARS.between(date1, date2);
        int diffInDaysInt = Math.abs((int) diffInDays);
        System.out.println(diffInDaysInt);
        if (diffInDaysInt < 18){
            errorLabel.setText("Sie müssen mindestens 18 Jahre alt sein");
            return;
        }
        PersonConnector personConnector = new PersonConnector();
        List<Person> personList = personConnector.datenbankSuchAlles();
        for (Person person: personList){
            if (person.getEmail().equals(emailTxtfield.getText())){
                errorLabel.setText("Es existiert ein Gast mit diesen Email");
                return;
            }
        }

        Person guest = new Gast(firstNameTxtfield.getText().trim()
                , lastNameTxtfield.getText().trim()
                , emailTxtfield.getText().trim(),
                birthdayDateChooser.getValue()
                , telefonNrTxtfield.getText().trim()
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