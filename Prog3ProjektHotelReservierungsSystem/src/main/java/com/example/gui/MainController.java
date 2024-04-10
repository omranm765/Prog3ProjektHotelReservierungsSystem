package com.example.gui;

import com.example.prog3projekthotelreservierungssystem.Hotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML
    private BorderPane borderpane;
    @FXML
    private ZimmerController zimmerController;
    @FXML
    private GaesteController gaesteController;
    @FXML
    private DashboardController dashboardController;

    @FXML
    void onClickOpenGuestDashboard(ActionEvent event) throws IOException {
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/Gaeste.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent node = fxmlLoader.load();
        gaesteController = fxmlLoader.getController();
        gaesteController.updateListView(Hotel.getAllGasts());
        borderpane.setCenter(node);
    }

    @FXML
    void onClickOpenBuchungDashboard(ActionEvent event) throws IOException {
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/Dashboard.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent node = fxmlLoader.load();
        dashboardController = fxmlLoader.getController();
        dashboardController.updateListView(Hotel.getAllBuchungen());

        borderpane.setCenter(node);
    }

    @FXML
    void onClickOpenZimmerDashboard(ActionEvent event) throws IOException {
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/Zimmern.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent node = fxmlLoader.load();
        zimmerController = fxmlLoader.getController();
        zimmerController.updateListView(Hotel.getAllZimmer());
        borderpane.setCenter(node);
    }
    @FXML
    public void initialize() {
        try {
            onClickOpenZimmerDashboard(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
