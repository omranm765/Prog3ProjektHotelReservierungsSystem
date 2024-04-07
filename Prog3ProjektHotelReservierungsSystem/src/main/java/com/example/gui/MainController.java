package com.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML
    private Button dashboard;

    @FXML
    private Button guest;

    @FXML
    private Button zimmer;

    @FXML
    private Button addBtn;

    @FXML
    private BorderPane borderpane;

    @FXML
    void addBtn(ActionEvent event) {

    }

    @FXML
    void onClickOpenGuestDashboard(ActionEvent event) throws IOException {
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/Guests.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent node = fxmlLoader.load();
        borderpane.setCenter(node);
    }

    @FXML
    void onClickOpenDashboard(ActionEvent event) throws IOException {
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/Dashboard.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent node = fxmlLoader.load();
        borderpane.setCenter(node);
    }

    @FXML
    void onClickOpenZimmerDashboard(ActionEvent event) throws IOException {
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/Rooms.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent node = fxmlLoader.load();
        borderpane.setCenter(node);
    }
}
