package com.example.gui;

import com.example.prog3projekthotelreservierungssystem.Hotel;
import com.example.prog3projekthotelreservierungssystem.HotelException;
import com.example.prog3projekthotelreservierungssystem.Person;
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

public class MitarbeiterController {

    @FXML
    private ChoiceBox<?> bookingChoiceBox;

    @FXML
    private Button cancelBtn;

    @FXML
    private ListView<Person> listView;

    public void onClickAddMitarbeiter(ActionEvent actionEvent) throws IOException {
        String windowTitle = "Add Mitarbeiter";
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/addMitarbeiter.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(root));

        AddMitarbeiterController addMitarbeiterController = fxmlLoader.getController();
        addMitarbeiterController.setStage(stage);
        addMitarbeiterController.setMitarbeiterController(this);

        stage.showAndWait();
    }

    public void onClickDeleteMitarbeiter(ActionEvent actionEvent) {
        try {
            Person selectedPerson = listView.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                Hotel.GastEntfernen(selectedPerson);
                updateListView(Hotel.getAllGasts());
            } else {
                System.out.println("Bitte wählen Sie ein Mitarbeiter zum Löschen aus.");
            }
        } catch (HotelException e) {
            System.out.println("Fehler beim Löschen des Mitarbeiter: " + e.getMessage());
        }
    }

    public void updateListView(List<Person> personList) {
        if (personList != null) {
            ObservableList<Person> observablePersonList = FXCollections.observableArrayList(personList);
            listView.setItems(observablePersonList);
        }
    }
}
