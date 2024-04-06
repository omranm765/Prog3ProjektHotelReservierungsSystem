package com.example.gui;

import com.example.prog3projekthotelreservierungssystem.Hotel;
import com.example.prog3projekthotelreservierungssystem.HotelException;
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
        String roomNr = roomNrTxtfield.getText();
        String price = costTxtfield.getText();

        if (roomNrTxtfield.getText().trim().isEmpty() || costTxtfield.getText().trim().isEmpty())
        {
            throw  new HotelException("Sie dürfen nicht leer sein");
        }

        Zimmer zimmer = new Zimmer(Integer.parseInt(roomNr),floorChoiceBox.getValue() ,areaChoicheBox.getValue() , Double.parseDouble(price));

        Hotel.zimmerHinzufuegen(zimmer);
    }
}