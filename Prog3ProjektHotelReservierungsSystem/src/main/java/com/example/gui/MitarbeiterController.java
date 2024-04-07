package com.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

public class MitarbeiterController {

    @FXML
    private ChoiceBox<?> bookingChoiceBox;

    @FXML
    private Button cancelBtn;

    @FXML
    private ListView<?> listView;

    @FXML
    void onClickCancelBooking(ActionEvent event) {

    }
}
