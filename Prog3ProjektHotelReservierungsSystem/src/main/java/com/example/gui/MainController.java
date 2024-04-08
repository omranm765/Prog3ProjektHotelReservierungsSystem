package com.example.gui;

import com.example.prog3projekthotelreservierungssystem.Hotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML
    private BorderPane borderpane;
    @FXML
    private RoomsController roomsController;
    @FXML
    private GuestsController guestsController;
    @FXML
    private DashboardController dashboardController;

    @FXML
    void addBuchungBtn(ActionEvent event) throws IOException {
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

        stage.showAndWait();
    }

    @FXML
    void onClickOpenGuestDashboard(ActionEvent event) throws IOException {
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/Guests.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent node = fxmlLoader.load();
        guestsController = fxmlLoader.getController();
        guestsController.updateListView(Hotel.getAllGasts());
        borderpane.setCenter(node);
    }

    @FXML
    void onClickOpenDashboard(ActionEvent event) throws IOException {
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/Dashboard.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent node = fxmlLoader.load();
        dashboardController = fxmlLoader.getController();
        dashboardController.updateListView(Hotel.getAllBuchungen());
        borderpane.setCenter(node);
    }

    @FXML
    void onClickOpenZimmerDashboard(ActionEvent event) throws IOException {
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/Rooms.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent node = fxmlLoader.load();
        roomsController = fxmlLoader.getController();
        roomsController.updateListView(Hotel.getAllZimmer());
        borderpane.setCenter(node);
    }
}
