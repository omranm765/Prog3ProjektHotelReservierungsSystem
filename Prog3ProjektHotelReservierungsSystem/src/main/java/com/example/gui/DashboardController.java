package com.example.gui;

import com.example.database.BuchungConnector;
import com.example.prog3projekthotelreservierungssystem.Buchung;
import com.example.prog3projekthotelreservierungssystem.Hotel;
import com.example.prog3projekthotelreservierungssystem.HotelException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

//Dashboard von die Buchungen
public class DashboardController {

    @FXML
    private ChoiceBox<String> bookingChoiceBox;

    @FXML
    private ListView<Buchung> listView;
    private Stage stage;
    @FXML
    private Label errorLabel;

    @FXML
    void onClickCancelBooking(ActionEvent event) {
        Buchung selectedBuchung = listView.getSelectionModel().getSelectedItem();
        if (selectedBuchung != null) {
            errorLabel.setText("");
            selectedBuchung.buchungStornieren();
            BuchungConnector buchungConnector = new BuchungConnector();
            buchungConnector.datenbankAktualisieren(selectedBuchung);
            updateListView(Hotel.getAllBuchungen());
        } else {
            errorLabel.setText("Bitte wählen Sie ein Buchung zum Stornieren aus.");
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
        listView.setCellFactory(list -> new ListCell<Buchung>() {
            @Override
            protected void updateItem(Buchung buchung, boolean leer) {
                super.updateItem(buchung, leer);

                if (leer || buchung == null) {
                    setText(null);
                } else {
                    setText(buchung.toString());
                    if (buchung.isStorniert()) {
                        setTextFill(Color.RED);
                    } else {
                        setTextFill(Color.BLACK);
                    }
                }
            }
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
                    }
                }
                listView.setItems(observableStorniertBuchungList);
            } else {
                ObservableList<Buchung> observableAktivBuchungList = FXCollections.observableArrayList();
                for (Buchung buchung : buchungList) {
                    if (!buchung.isStorniert()) {
                        observableAktivBuchungList.add(buchung);
                    }
                }
                listView.setItems(observableAktivBuchungList);
            }
        }
    }

    @FXML
    public void onClickAddBuchung(ActionEvent actionEvent) throws IOException {
        String windowTitle = "Add Room";
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/addBuchung.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(root));

        AddBuchungController addBuchungController = fxmlLoader.getController();
        addBuchungController.setStage(stage);
        addBuchungController.fillRoomChoiceBox();
        addBuchungController.setDashboardController(this);

        stage.showAndWait();
    }

    @FXML
    void onClickRechnungErstellen(ActionEvent event) throws HotelException {
        Buchung selectedBuchung = listView.getSelectionModel().getSelectedItem();
        if (selectedBuchung != null) {
            if (selectedBuchung.isStorniert()){
                errorLabel.setText("Die Buchung ist Storniert");
                return;
            }
            errorLabel.setText("");
            selectedBuchung.rechnungErstellen();
            errorLabel.setText("Rechnung erfolgreich erstellt: Rechnung_"+
                    selectedBuchung.getRechnung().getRechnungsID() +".txt");
        } else {
            errorLabel.setText("Bitte wählen Sie ein Buchung um Rechnung zu erstellen aus.");
        }
    }
}
