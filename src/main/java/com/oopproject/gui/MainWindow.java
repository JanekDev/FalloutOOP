package com.oopproject.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.stage.Screen;
import javafx.stage.WindowEvent;

/**
 * Main window of the application.
 */
public class MainWindow extends Application {
    /**
     * Entry point starting the application.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("MainWindow.fxml"));
        double window_size = (double)((int)Screen.getPrimary().getBounds().getHeight()) * 0.8;
        Scene scene = new Scene(fxmlLoader.load(), window_size*1.1, window_size);
        stage.setTitle("OOP Project");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                // get the controller
                MainWindowController controller = fxmlLoader.getController();
                // stop the simulation
                controller.terminateSimulation();
                Platform.exit();
                System.exit(0);
            }
        });
    }
}