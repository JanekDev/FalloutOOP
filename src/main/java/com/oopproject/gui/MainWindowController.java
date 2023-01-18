package com.oopproject.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import com.oopproject.world.World;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The main window controller.
 */
public class MainWindowController implements Initializable {
    private Mapper animalMapper;
    private World welcometothejungle;
    @FXML
    private TextField animalTextField;

    @FXML
    private VBox mainVBox;

    @FXML
    private Canvas mapCanvas;

    @FXML
    private Button preyButton;

    @FXML
    private Button predatorButton;

    @FXML
    private Button startButton;

    @FXML
    private Label propertiesViewLabel;

    @FXML
    private TitledPane mapTitledPane;

    /**
     * Initializes the controller class.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double height = (double)((int) Screen.getPrimary().getBounds().getHeight() * 0.8 - 220);
        mapTitledPane.setPrefHeight(height);
        mapTitledPane.setMaxHeight(height);
        animalMapper = new Mapper(mapCanvas, height);
        welcometothejungle = new World(animalMapper);
    }

    /**
     * Start/stop button handler.
     */
    public void start(){
        if (startButton.getText().equals("START")){
            startButton.setText("STOP");
            welcometothejungle.run();
        }
        else{
            startButton.setText("START");
            welcometothejungle.stop();
        }
    }

    /**
     * Add prey button handler.
     */
    public void addPrey(){
        welcometothejungle.addPrey(animalTextField.getText());
    }

    /**
     * Add predator button handler.
     */
    public void addPredator(){
        welcometothejungle.addPredator(animalTextField.getText());
    }

    /**
     * Terminate the simulation.
     */
    public void terminateSimulation(){
        welcometothejungle.terminate();
    }
}
