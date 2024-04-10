package com.example.gui;

import com.example.prog3projekthotelreservierungssystem.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class GaesteController {
    @FXML
    private ListView<Person> listView;

    @FXML
    void onClickAddGuest(ActionEvent event) throws IOException {
        String windowTitle = "Add Gast";
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/addGast.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(root));

        AddGastController addGastController = fxmlLoader.getController();
        addGastController.setStage(stage);
        addGastController.setGuestsController(this);
        stage.showAndWait();
    }

    @FXML
    void onClickDeleteGuest(ActionEvent event) {
        try {
            Person selectedPerson = listView.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                Hotel.GastEntfernen(selectedPerson);
                updateListView(Hotel.getAllGasts());
            } else {
                System.out.println("Bitte wählen Sie ein Gast zum Löschen aus.");
            }
        } catch (HotelException e) {
            System.out.println("Fehler beim Löschen des Gasts: " + e.getMessage());
        }
    }

    public void updateListView(List<Person> personList) {
        if (personList != null) {
            ObservableList<Person> observablePersonList = FXCollections.observableArrayList(personList);
            listView.setItems(observablePersonList);
        }
    }
}
