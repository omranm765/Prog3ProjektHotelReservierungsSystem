package com.example.gui;

import com.example.database.BuchungConnector;
import com.example.prog3projekthotelreservierungssystem.Buchung;
import com.example.prog3projekthotelreservierungssystem.Hotel;
import com.example.prog3projekthotelreservierungssystem.HotelException;
import com.example.prog3projekthotelreservierungssystem.Zimmer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class DashboardController {

    @FXML
    private ChoiceBox<String> bookingChoiceBox;

    @FXML
    private ListView<Buchung> listView;
    private Stage stage;

    @FXML
    void onClickCancelBooking(ActionEvent event) {
        Buchung selectedBuchung = listView.getSelectionModel().getSelectedItem();
        if (selectedBuchung != null) {
            selectedBuchung.buchungStornieren();
            BuchungConnector buchungConnector = new BuchungConnector();
            buchungConnector.datenbankAktualisieren(selectedBuchung);
            updateListView(Hotel.getAllBuchungen());
        } else {
            System.out.println("Bitte wählen Sie ein Zimmer zum Löschen aus.");
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void initialize() {
        bookingChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateListView(Hotel.getAllBuchungen());
        });
    }

    public void updateListView(List<Buchung> buchungList) {
        if (buchungList != null) {
            if (bookingChoiceBox.getValue().equals("Alle")) {
                ObservableList<Buchung> observableAlleBuchungList = FXCollections.observableArrayList(buchungList);
                listView.setItems(observableAlleBuchungList);
            } else if (bookingChoiceBox.getValue().equals("Storniert")) {
                ObservableList<Buchung> observableStorniertBuchungList = FXCollections.observableArrayList();
                for (Buchung buchung : buchungList) {
                    if (buchung.isStorniert()) {
                        observableStorniertBuchungList.add(buchung);
                        listView.setItems(observableStorniertBuchungList);
                    }
                }
            } else {
                ObservableList<Buchung> observableAktivBuchungList = FXCollections.observableArrayList();
                for (Buchung buchung : buchungList) {
                    if (!buchung.isStorniert()) {
                        observableAktivBuchungList.add(buchung);
                        listView.setItems(observableAktivBuchungList);
                    }
                }
            }
        }
    }

    @FXML
    public void onClickAddBuchung(ActionEvent actionEvent) throws IOException {
        String windowTitle = "Add Room";
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/newBooking.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(root));

        NewBookingController newBookingController = fxmlLoader.getController();
        newBookingController.setStage(stage);
        newBookingController.fillRoomChoiceBox();
        newBookingController.setDashboardController(this);

        stage.showAndWait();
    }

    @FXML
    void onClickRechnungErstellen(ActionEvent event) {
        Buchung selectedBuchung = listView.getSelectionModel().getSelectedItem();
        if (selectedBuchung != null) {
            selectedBuchung.rechnungErstellen();
        } else {
            System.out.println("Bitte wählen Sie ein Zimmer zum Löschen aus.");
        }
    }
}
