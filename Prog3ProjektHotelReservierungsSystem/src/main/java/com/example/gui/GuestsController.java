package com.example.gui;

import com.example.prog3projekthotelreservierungssystem.Person;
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

public class GuestsController {
    @FXML
    private ListView<Person> listView;

    @FXML
    void onClickCancelBooking(ActionEvent event) throws IOException {

    }

    @FXML
    void onClickAddGuest(ActionEvent event) throws IOException {
        String windowTitle = "Add Room";
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/addGuest.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(root));

        AddGuestController addGuestController = fxmlLoader.getController();
        addGuestController.setStage(stage);
        addGuestController.setGuestsController(this);

        stage.showAndWait();
    }

    @FXML
    void onClickDeleteGuest(ActionEvent event) {

    }

    public void updateListView(List<Person> personList) {
        if (personList != null) {
            ObservableList<Person> observablePersonList = FXCollections.observableArrayList(personList);
            listView.setItems(observablePersonList);
        }
    }
}
