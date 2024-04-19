package com.example.gui;

import com.example.database.BuchungConnector;
import com.example.database.PersonConnector;
import com.example.prog3projekthotelreservierungssystem.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddBuchungController {


    @FXML
    private DatePicker arrivalDateChooser;

    @FXML
    private DatePicker departureDateChooser;

    @FXML
    private TextField guestIdTxtField;

    @FXML
    private ChoiceBox<Integer> roomChoiceBox;
    private Stage stage;
    @FXML
    private Label errorLabel;
    private DashboardController dashboardController;

    @FXML
    void initialize(){
        guestIdTxtField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    onClickAddBooking(new ActionEvent());
                } catch (HotelException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        arrivalDateChooser.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    onClickAddBooking(new ActionEvent());
                } catch (HotelException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        departureDateChooser.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    onClickAddBooking(new ActionEvent());
                } catch (HotelException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        roomChoiceBox.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                try {
                    onClickAddBooking(new ActionEvent());
                } catch (HotelException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    void onClickAddBooking(ActionEvent event) throws HotelException {
        if (guestIdTxtField.getText().trim().isEmpty()
                || roomChoiceBox.getItems().isEmpty()
                || departureDateChooser.getValue() == null
                || arrivalDateChooser.getValue() == null) {
            errorLabel.setText("Bitte füllen sie die Felder aus");
            return;
        }
        if (!guestIdTxtField.getText().matches("[0-9]+")){
            errorLabel.setText("GuestID darf nur Zahlen enthalten");
            return;
        }
        if (arrivalDateChooser.getValue().isBefore(LocalDate.now())){
            errorLabel.setText("Ankunftsdatum muss mindestens aktuell sein!");
            return;
        }
        if (arrivalDateChooser.getValue().isAfter(departureDateChooser.getValue())){
            errorLabel.setText("Ankunftsdatum muss vor Abreisedatum sein!");
            return;
        }
        BuchungConnector buchungConnector = new BuchungConnector();
        List<Buchung> buchungList = buchungConnector.datenbankSuchAlles();
        for (Buchung b : buchungList) {
            if (b.getZimmerNr() == roomChoiceBox.getValue() && !b.isStorniert()) {
                errorLabel.setText("Es existiert ein Buchung mit dieser ZimmerNr");
                return;
            }
        }
        PersonConnector personConnector = new PersonConnector();
        List<Person> personList = personConnector.datenbankSuchAlles();
        boolean found = false;
        for (Person person: personList) {
            if (person.getId() == Integer.parseInt(guestIdTxtField.getText())){
                found = true;
            }
        }
        if (!found){
            errorLabel.setText("Gast existiert nicht");
            return;
        }
        int id = Integer.parseInt(guestIdTxtField.getText());
        Person guest = Hotel.getPersonById(id);
        Buchung buchung = new Buchung(guest, arrivalDateChooser.getValue(),
                departureDateChooser.getValue(), roomChoiceBox.getValue());

        Hotel.buchungHinzufuegen(buchung);
        dashboardController.updateListView(Hotel.getAllBuchungen());
        stage.close();
    }

    public void fillRoomChoiceBox() {
        List<Integer> roomNumbers = getRoomNumbersFromDatabase();
        if (roomNumbers != null) {
            roomChoiceBox.getItems().addAll(roomNumbers);
        }
    }

    private List<Integer> getRoomNumbersFromDatabase() {
        List<Zimmer> zimmerList = Hotel.getAllZimmer();
        List<Integer> roomNumbersList = new ArrayList<>();
        for (Zimmer z : zimmerList) {
            if (z != null) {
                roomNumbersList.add(z.getZimmerNr());
            }
        }
        return roomNumbersList;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public DashboardController getDashboardController() {
        return dashboardController;
    }
}
