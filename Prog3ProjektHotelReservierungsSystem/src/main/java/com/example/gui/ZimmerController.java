package com.example.gui;

import com.example.database.ZimmerConnector;
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
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class ZimmerController {
    @FXML
    public TextField zimmerNrSearchTxtField;
    @FXML
    private ListView<Zimmer> listView;
    @FXML
    private ChoiceBox<Integer> floorChoiceBox;
    @FXML
    private Label errorLabel;

    @FXML
    void onClickAddRoom(ActionEvent event) throws IOException {
        String windowTitle = "Add Zimmer";
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/addZimmer.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(root));

        AddZimmerController addZimmerController = fxmlLoader.getController();
        addZimmerController.setStage(stage);
        addZimmerController.setRoomsController(this);

        stage.showAndWait();
    }

    @FXML
    void initialize() {
        zimmerNrSearchTxtField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                onClickSuchZimmerNachNummer();
            }
        });
        floorChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateListView(Hotel.getAllZimmer());
        });
        listView.setOnKeyPressed(keyEvent -> {
            if (listView.getSelectionModel().getSelectedItem() != null) {
                if (keyEvent.getCode() == KeyCode.DELETE) {
                    onClickDeleteRoom(new ActionEvent());
                }
            }
        });

    }

    public void updateListView(List<Zimmer> zimmerList) {
        if (zimmerList != null) {
            if (floorChoiceBox.getValue() == 1) {
                ObservableList<Zimmer> observableZimmerList = FXCollections.observableArrayList();
                for (Zimmer zimmer : zimmerList) {
                    if (zimmer.getEtage() == 1) {
                        observableZimmerList.add(zimmer);
                    }
                }
                listView.setItems(observableZimmerList);
            } else {
                ObservableList<Zimmer> observableZimmerList = FXCollections.observableArrayList();
                for (Zimmer zimmer : zimmerList) {
                    if (zimmer.getEtage() == 2) {
                        observableZimmerList.add(zimmer);
                    }
                }
                listView.setItems(observableZimmerList);
            }
        }
    }

    public ListView<?> getListView() {
        return listView;
    }

    public void setListView(ListView<Zimmer> listView) {
        this.listView = listView;
    }

    @FXML
    void onClickDeleteRoom(ActionEvent event) {
        try {
            Zimmer selectedZimmer = listView.getSelectionModel().getSelectedItem();
            if (selectedZimmer != null) {
                errorLabel.setText("");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Löschen bestätigen");
                alert.setHeaderText("Möchten Sie dieses Zimmer wirklich löschen?");
                alert.setContentText("Dieser Prozess kann nicht rückgängig gemacht werden! o_O");
                ButtonType buttonTypeYes = new ButtonType("Ja");
                ButtonType buttonTypeNo = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == buttonTypeYes) {
                    Hotel.zimmerEntfernen(selectedZimmer);
                    updateListView(Hotel.getAllZimmer());
                }
            } else {
                errorLabel.setText("Bitte wählen Sie ein Zimmer zum Löschen aus.");
            }
        } catch (HotelException e) {
            System.out.println("Fehler beim Löschen des Zimmers: " + e.getMessage());
        }
    }

    @FXML
    void onClickRefreshListview() {
        this.updateListView(Hotel.getAllZimmer());
    }

    @FXML
    void onClickSuchZimmerNachNummer() {
        ZimmerConnector zimmerConnector = new ZimmerConnector();
        String text = zimmerNrSearchTxtField.getText().trim();
        if (text.isEmpty()){
            errorLabel.setText("Zimmer nicht gefunden");
            return;
        }
        Zimmer zimmer = zimmerConnector.datenbankSuchNachNummer(Integer.parseInt(text));
        if (zimmer == null){
            errorLabel.setText("Zimmer nicht gefunden");
            return;
        }
        ObservableList<Zimmer> observableList = FXCollections.observableArrayList();
        observableList.add(zimmer);
        listView.setItems(observableList);
    }
}
