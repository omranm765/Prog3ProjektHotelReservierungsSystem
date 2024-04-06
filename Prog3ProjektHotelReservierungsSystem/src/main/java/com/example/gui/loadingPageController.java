package com.example.gui;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.ProgressIndicator;

public class loadingPageController {

    @FXML
    private ImageView image;

    @FXML
    private ProgressIndicator progressIndicator;

    // Methode zum Aktualisieren des Fortschrittsindikators
    public void updateProgress(double progress) {
        progressIndicator.setProgress(progress);
    }
}
