package com.example.gui;

import com.example.database.PersonConnector;
import com.example.prog3projekthotelreservierungssystem.*;
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

public class GaesteController {
    @FXML
    private ListView<Person> listView;
    private Person selectedPerson;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField emailSearchTxtField;
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
                errorLabel.setText("");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Löschen bestätigen");
                alert.setHeaderText("Möchten Sie dieses Gast wirklich löschen?");
                alert.setContentText("Dieser Prozess kann nicht rückgängig gemacht werden! o_O");
                ButtonType buttonTypeYes = new ButtonType("Ja");
                ButtonType buttonTypeNo = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == buttonTypeYes) {
                    Hotel.GastEntfernen(selectedPerson);
                    updateListView(Hotel.getAllGasts());
                }
            } else {
                errorLabel.setText("Bitte wählen Sie ein Gast zum Löschen aus.");
            }
        } catch (HotelException e) {
            System.out.println("Fehler beim Löschen des Gasts: " + e.getMessage());
        }
    }

    @FXML
    void onClickEditGast(ActionEvent event) throws IOException {
        String windowTitle = "Edit Gast";
        URL fxmlName = getClass().getResource("/com/example/prog3projekthotelreservierungssystem/editGast.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlName);
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(root));

        EditGastController editGastController = fxmlLoader.getController();
        editGastController.setStage(stage);
        editGastController.setGuestsController(this);
        selectedPerson = listView.getSelectionModel().getSelectedItem();
        if (selectedPerson == null){
            errorLabel.setText("Bitte wählen sie zuerst ein Person zum ändern");
                    return;
        } else {
            errorLabel.setText("");
        }
        editGastController.setSelectedPerson(selectedPerson);
        editGastController.setListView(listView);
        stage.showAndWait();
    }

    public void updateListView(List<Person> personList) {
        if (personList != null) {
            ObservableList<Person> observablePersonList = FXCollections.observableArrayList(personList);
            listView.setItems(observablePersonList);
        }
    }

    @FXML
    void onClickSuchGastNachEmail(){
        PersonConnector personConnector = new PersonConnector();
        Person gast = personConnector.datenbankSuchNachEmail(emailSearchTxtField.getText().trim());
        if (gast == null){
            errorLabel.setText("Gast nicht gefunden");
            return;
        }
        ObservableList<Person> observableList = FXCollections.observableArrayList();
        observableList.add(gast);
        listView.setItems(observableList);
    }

    @FXML
    void initialize() {
        emailSearchTxtField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                onClickSuchGastNachEmail();
            }
        });
        listView.setOnKeyPressed(keyEvent -> {
            if (listView.getSelectionModel().getSelectedItem() != null){
                if (keyEvent.getCode() == KeyCode.DELETE){
                    onClickDeleteGuest(new ActionEvent());
                }
            }
        });

    }

    @FXML
    void onClickRefreshListview(){
        this.updateListView(Hotel.getAllGasts());
    }

    public ListView<Person> getListView(){
        return listView;
    }
}
