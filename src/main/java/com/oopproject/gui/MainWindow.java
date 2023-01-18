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
 * @author Michal
 *
 */
public class MainWindow extends Application {
    /**
     * Entry point starting the application.
     * @param stage the stage to be shown
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("MainWindow.fxml"));
        double windowSize = (double)((int)Screen.getPrimary().getBounds().getHeight()*0.9);
        Scene scene = new Scene(fxmlLoader.load(), windowSize*1.1, windowSize);
        stage.setTitle("Fallout 0");
        stage.getIcons().add(new javafx.scene.image.Image("file:src/main/resources/images/icon.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                // get the controller
                MainWindowController controller = fxmlLoader.getController();
                // stop the simulation
                //controller.terminateSimulation();
                Platform.exit();
                System.exit(0);
            }
        });
    }
}