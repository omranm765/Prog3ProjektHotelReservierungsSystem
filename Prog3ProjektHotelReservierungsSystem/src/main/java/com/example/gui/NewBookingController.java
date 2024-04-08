package com.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class NewBookingController {


    @FXML
    private DatePicker arrivalDateChooser;

    @FXML
    private DatePicker departureDateChooser;

    @FXML
    private ChoiceBox<Integer> floorChoiceBox;

    @FXML
    private TextField guestId;

    @FXML
    private TextField guestsCountTxtfield;

    @FXML
    private TextField priceTxtfield;

    @FXML
    private ChoiceBox<Integer> roomChoiceBox;

    @FXML
    void onClickAddBooking(ActionEvent event) {

    }
}
