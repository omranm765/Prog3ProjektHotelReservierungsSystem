package com.example.gui;

import com.example.prog3projekthotelreservierungssystem.Buchung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

public class DashboardController {

    @FXML
    private ChoiceBox<?> bookingChoiceBox;

    @FXML
    private ListView<Buchung> listView;
    private Stage stage;

    @FXML
    void onClickCancelBooking(ActionEvent event) {

    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void updateListView(List<Buchung> buchungList) {
        if (buchungList != null) {
            ObservableList<Buchung> observableBuchungList = FXCollections.observableArrayList(buchungList);
            listView.setItems(observableBuchungList);
        }
    }

}
