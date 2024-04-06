// NUR TEST

package com.example.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/prog3projekthotelreservierungssystem/loadingPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        stage.setTitle("Test");
        stage.setScene(scene);
        stage.show();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/com/example/prog3projekthotelreservierungssystem/Main.fxml"));
                        Scene mainScene = new Scene(mainLoader.load(), 1000, 800);
                        stage.setScene(mainScene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }, 3000);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
