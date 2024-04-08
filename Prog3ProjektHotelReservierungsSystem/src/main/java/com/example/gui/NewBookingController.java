package com.example.gui;

import com.example.database.PersonConnector;
import com.example.prog3projekthotelreservierungssystem.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

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
    private ChoiceBox<Integer> roomChoiceBox;
    private Stage stage;
    @FXML
    private Label errorLabel;
    private DashboardController dashboardController;

    @FXML
    void onClickAddBooking(ActionEvent event) throws HotelException {
        if (guestId.getText().trim().isEmpty()
                || roomChoiceBox.getItems().isEmpty()
                || floorChoiceBox.getItems().isEmpty() || departureDateChooser.getValue() == null
                || arrivalDateChooser.getValue() == null) {
            errorLabel.setText("Bitte füllen sie die Felder aus");
            return;
        }
        int id = Integer.parseInt(guestId.getText());
        Person guest = Hotel.getPersonById(id);
        Buchung buchung = new Buchung(guest, arrivalDateChooser.getValue(),
                departureDateChooser.getValue(), roomChoiceBox.getValue());

        Hotel.buchungHinzufuegen(buchung);
        dashboardController.updateListView(Hotel.getAllBuchungen());
        stage.close();
    }

    public void fillRoomChoiceBox() {
        List<Integer> roomNumbers = getRoomNumbersFromDatabase();
        if (roomNumbers != null) {
            roomChoiceBox.getItems().addAll(roomNumbers);
        }
    }

    private List<Integer> getRoomNumbersFromDatabase() {
        List<Zimmer> zimmerList = Hotel.getAllZimmer();
        List<Integer> roomNumbersList = new ArrayList<>();
        for (Zimmer z : zimmerList) {
            if (z != null) {
                roomNumbersList.add(z.getZimmerNr());
            }
        }
        return roomNumbersList;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public DashboardController getDashboardController() {
        return dashboardController;
    }
}
