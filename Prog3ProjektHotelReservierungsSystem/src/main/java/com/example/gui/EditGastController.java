package com.example.gui;

import com.example.prog3projekthotelreservierungssystem.Gast;
import com.example.prog3projekthotelreservierungssystem.Hotel;
import com.example.prog3projekthotelreservierungssystem.HotelException;
import com.example.prog3projekthotelreservierungssystem.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditGastController {

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
    private GaesteController gaesteController;
    private Stage stage;
    private ListView<Person> listView;
    private Person selectedPerson;

    @FXML
    void onClickSpeichern(ActionEvent event) throws HotelException {
        Person selectedPerson = listView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            if (lastNameTxtfield.getText().trim().isEmpty() || emailTxtfield.getText().trim().isEmpty()
                    || firstNameTxtfield.getText().trim().isEmpty() || telefonNrTxtfield.getText().trim().isEmpty()
                    || dateChooser.getValue() == null) {
                errorLabel.setText("Bitte füllen Sie alle Felder aus.");
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
            Hotel.gastAendern(selectedPerson, firstNameTxtfield.getText()
                    , lastNameTxtfield.getText(), emailTxtfield.getText(), dateChooser.getValue()
                    , telefonNrTxtfield.getText());
            gaesteController.updateListView(Hotel.getAllGasts());
            stage.close();
        } else {
            errorLabel.setText("Bitte wählen sie zuerst ein Gast zum ändern");
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setGuestsController(GaesteController gaesteController) {
        this.gaesteController = gaesteController;
    }

    public void setListView(ListView<Person> listView) {
        this.listView = listView;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }
}
