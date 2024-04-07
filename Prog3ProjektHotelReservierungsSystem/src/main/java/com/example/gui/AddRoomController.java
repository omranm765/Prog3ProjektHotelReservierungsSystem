package com.example.gui;

import com.example.prog3projekthotelreservierungssystem.Hotel;
import com.example.prog3projekthotelreservierungssystem.HotelException;
import com.example.prog3projekthotelreservierungssystem.Validator;
import com.example.prog3projekthotelreservierungssystem.Zimmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import static java.lang.Double.parseDouble;

public class AddRoomController {

    @FXML
    private Button addRoomBtn;

    @FXML
    private ChoiceBox<Integer> areaChoicheBox;

    @FXML
    private TextField costTxtfield;

    @FXML
    private ChoiceBox<Integer> floorChoiceBox;

    @FXML
    private TextField roomNrTxtfield;

    @FXML
    void onClickAddRoom(ActionEvent event) throws Exception {
        String roomNrString = roomNrTxtfield.getText();
        String priceString = costTxtfield.getText();

        Validator.check(roomNrString.trim().isEmpty() || priceString.trim().isEmpty(), "Bitte füllen Sie die Felder aus");

        try {
            int roomNr = Integer.parseInt(roomNrString);
            double price = Double.parseDouble(priceString);

            String areaString = areaChoicheBox.getValue().toString();
            int area = Integer.parseInt(areaString);

            Zimmer zimmer = new Zimmer(roomNr, floorChoiceBox.getValue(), area, price);

            Hotel.zimmerHinzufuegen(zimmer);
        } catch (NumberFormatException e) {
            System.err.println("Ungültige Eingabe: Stellen Sie sicher, dass Sie gültige Zahlen eingeben.");
        }
    }
}