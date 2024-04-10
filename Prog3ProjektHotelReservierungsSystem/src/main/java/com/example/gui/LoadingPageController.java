package com.example.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static javafx.application.Application.launch;

public class LoadingPageController extends Application {

    @FXML
    private ImageView image;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/prog3projekthotelreservierungssystem/loadingPage.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        Scene scene = new Scene(anchorPane);

        Image image = new Image("StartImage.jpg");
        ImageView imageView = new ImageView(image);
        stage.getIcons().add(image);

        imageView.fitWidthProperty().bind(scene.widthProperty());
        imageView.fitHeightProperty().bind(scene.heightProperty());

        anchorPane.getChildren().add(imageView);

        stage.setTitle("Syria Hotel");
        stage.setScene(scene);

        stage.setMaximized(true);
        stage.setResizable(false);

        stage.show();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/com/example/prog3projekthotelreservierungssystem/Main.fxml"));
                        Scene mainScene = new Scene(mainLoader.load(),1200, 880);
                        stage.setScene(mainScene);
                        stage.setResizable(true);
                        stage.setMaximized(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 1000);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
