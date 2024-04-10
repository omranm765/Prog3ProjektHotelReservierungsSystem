package com.example.gui;

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
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class RoomsController {
    @FXML
    private ListView<Zimmer> listView;
    @FXML
    void onClickAddRoom(ActionEvent event) throws IOException {
        String windowTitle = "Add Room";
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/addZimmer.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(root));

        AddRoomController addRoomController = fxmlLoader.getController();
        addRoomController.setStage(stage);
        addRoomController.setRoomsController(this);

        stage.showAndWait();
    }

    public void updateListView(List<Zimmer> zimmerList) {
        if (zimmerList != null) {
            ObservableList<Zimmer> observableZimmerList = FXCollections.observableArrayList(zimmerList);
            listView.setItems(observableZimmerList);
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
                Hotel.zimmerEntfernen(selectedZimmer);
                updateListView(Hotel.getAllZimmer());
            } else {
                System.out.println("Bitte wählen Sie ein Zimmer zum Löschen aus.");
            }
        } catch (HotelException e) {
            System.out.println("Fehler beim Löschen des Zimmers: " + e.getMessage());
        }
    }
}
