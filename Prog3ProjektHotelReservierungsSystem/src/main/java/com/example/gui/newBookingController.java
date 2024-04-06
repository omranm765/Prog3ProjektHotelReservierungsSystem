package com.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class newBookingController {

    @FXML
    private Button addBookingBtn;

    @FXML
    private DatePicker arrivalDateChooser;

    @FXML
    private Button cancelProcessBtn;

    @FXML
    private DatePicker departureDateChooser;

    @FXML
    private ChoiceBox<?> floorChoiceBox;

    @FXML
    private TextField guestId;

    @FXML
    private TextField guestsCountTxtfield;

    @FXML
    private TextField priceTxtfield;

    @FXML
    private ChoiceBox<?> roomChoiceBox;

    @FXML
    void onClickAddBooking(ActionEvent event) {

    }

    @FXML
    void onClickCancelProccess(ActionEvent event) {

    }
}
