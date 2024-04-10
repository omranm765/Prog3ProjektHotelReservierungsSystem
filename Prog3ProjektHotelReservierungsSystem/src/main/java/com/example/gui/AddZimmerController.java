package com.example.gui;

import com.example.prog3projekthotelreservierungssystem.Hotel;
import com.example.prog3projekthotelreservierungssystem.Zimmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddZimmerController {

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
    private Stage stage;
    private ZimmerController zimmerController;
    @FXML
    private Label errorLabel;

    @FXML
    void onClickAddRoom(ActionEvent event) throws Exception {
        String roomNrString = roomNrTxtfield.getText();
        String priceString = costTxtfield.getText();

        if (roomNrString.trim().isEmpty() || priceString.trim().isEmpty())
        errorLabel.setText("Bitte füllen Sie die Felder aus");

        try {
            int roomNr = Integer.parseInt(roomNrString);
            double price = Double.parseDouble(priceString);

            String areaString = areaChoicheBox.getValue().toString();
            int area = Integer.parseInt(areaString);

            Zimmer zimmer = new Zimmer(roomNr, floorChoiceBox.getValue(), area, price);

            Hotel.zimmerHinzufuegen(zimmer);
            zimmerController.updateListView(Hotel.getAllZimmer());
            stage.close();

        } catch (NumberFormatException e) {
            System.err.println("Ungültige Eingabe: Stellen Sie sicher, dass Sie gültige Zahlen eingeben.");
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setRoomsController(ZimmerController zimmerController) {
        this.zimmerController = zimmerController;
    }
}