package com.example.gui;

import com.example.database.ZimmerConnector;
import com.example.prog3projekthotelreservierungssystem.Hotel;
import com.example.prog3projekthotelreservierungssystem.HotelException;
import com.example.prog3projekthotelreservierungssystem.Validator;
import com.example.prog3projekthotelreservierungssystem.Zimmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.List;

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
    void initialize() {
        roomNrTxtfield.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    onClickAddRoom(new ActionEvent());
                } catch (HotelException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        costTxtfield.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    onClickAddRoom(new ActionEvent());
                } catch (HotelException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    void onClickAddRoom(ActionEvent event) throws HotelException {
        String roomNrString = roomNrTxtfield.getText().trim();
        String priceString = costTxtfield.getText().trim();

        if (roomNrString.isEmpty() || priceString.isEmpty()) {
            errorLabel.setText("Bitte füllen Sie die Felder aus");
            return;
        }
        if (!roomNrString.matches("[0-9]+")) {
            errorLabel.setText("ZimmerNr darf nur Zahlen enthalten");
            return;
        }
        ZimmerConnector zimmerConnector = new ZimmerConnector();
        List<Zimmer> tempZimmer = zimmerConnector.datenbankSuchAlles();
        for (Zimmer z : tempZimmer) {
            if (z.getZimmerNr() == Integer.parseInt(roomNrString)) {
                errorLabel.setText("Es existiert ein Zimmer mit diesen ZimmerNr");
                return;
            }
        }
        if (!priceString.matches("[0-9]+(\\.([0-9]{1,2}))?")) {
            errorLabel.setText("Preis darf nur Zahlen und komma enthalten und 2 zahlen nach dem komma");
            return;
        }
        int roomNr = Integer.parseInt(roomNrString);
        double price = Double.parseDouble(priceString);

        String areaString = areaChoicheBox.getValue().toString();
        int area = Integer.parseInt(areaString);

        Zimmer zimmer = new Zimmer(roomNr, floorChoiceBox.getValue(), area, price);

        Hotel.zimmerHinzufuegen(zimmer);
        zimmerController.updateListView(Hotel.getAllZimmer());
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setRoomsController(ZimmerController zimmerController) {
        this.zimmerController = zimmerController;
    }
}