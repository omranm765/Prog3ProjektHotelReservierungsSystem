package com.example.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GastsDashboardController {

    @FXML
    private Button addGuestBtn;

    @FXML
    private TableColumn<?, ?> birthdateColumn;

    @FXML
    private Button editGuestBtn;

    @FXML
    private TableColumn<?, ?> emailColumn;

    @FXML
    private TextField firstNameTxtfield;

    @FXML
    private TableColumn<?, ?> lastNameColumn;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableView<?> tabelView;

    @FXML
    private TableColumn<?, ?> telefonNrColumn;

    @FXML
    void onClickEditGuest(ActionEvent event) {

    }

    @FXML
    void onClickaddGuest(ActionEvent event) {

    }

}
