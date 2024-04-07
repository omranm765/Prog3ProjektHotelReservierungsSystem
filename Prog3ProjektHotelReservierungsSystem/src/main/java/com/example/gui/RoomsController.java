package com.example.gui;

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

public class RoomsController {

    @FXML
    private ChoiceBox<?> bookingChoiceBox;

    @FXML
    private Button cancelBtn;

    @FXML
    private ListView<?> listView;

    @FXML
    void onClickAddRoom(ActionEvent event) throws IOException {
        String windowTitle = "Add Room";
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/addRoom.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(root));

        stage.showAndWait();

    }


    @FXML
    void onClickCancelBooking(ActionEvent event) {

    }

    @FXML
    void onClickDeleteRoom(ActionEvent event) {

    }

}
