package com.oopproject.gui;

import com.oopproject.world.animals.Animal;
import com.oopproject.world.animals.Prey;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import com.oopproject.world.World;
import javafx.util.Pair;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The main window controller. Managed by The Institute, so watch out for any synth attacks.
 */
public class MainWindowController implements Initializable {
    private Mapper animalMapper;
    private World welcomeToTheJungle;
    @FXML
    private TextField animalTextField;
    @FXML
    private Canvas mapCanvas;
    @FXML
    private Button startButton;
    @FXML
    private Label objectLabel;
    @FXML
    private Canvas objectCanvas;
    @FXML
    private Canvas bombsCanvas;
    @FXML
    private TitledPane mapTitledPane;
    private Pair<Integer, Integer> selection;
    private Animal selectedAnimal;
    private Image nukaImage;
    private Image bombImage;

    /**
     * Initializes the controller class.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double height = (double)((int) Screen.getPrimary().getBounds().getHeight()*0.9- 315);
        mapTitledPane.setPrefHeight(height);
        mapTitledPane.setMaxHeight(height);
        nukaImage = new Image("file:src/main/resources/images/nuka.png");
        //draw the image on the canvas, keeping the aspect ratio of the image
        objectCanvas.getGraphicsContext2D().drawImage(nukaImage, 0, 0, objectCanvas.getWidth(), objectCanvas.getHeight());
        bombImage = new Image("file:src/main/resources/images/bombs.png");
        bombsCanvas.getGraphicsContext2D().drawImage(bombImage, 0, 0, bombsCanvas.getWidth(), bombsCanvas.getHeight());
        animalMapper = new Mapper(mapCanvas, height);
        welcomeToTheJungle = new World(animalMapper);
        getClick();
        // start the music
    }

    /**
     * Gets the postion of the click on the map and displays the animal in the given box.
     */
    public void getClick(){
        mapCanvas.setOnMouseClicked(mouseEvent -> {
            selection = new Pair<>((int) (mouseEvent.getX() / animalMapper.getxStep()), (int) (mouseEvent.getY() / animalMapper.getyStep()));
            objectLabel.setText(welcomeToTheJungle.getObjectInfo(selection.getKey(), selection.getValue()));
            for (int i = 0; i < welcomeToTheJungle.getAnimals().size(); i++) {
                if (welcomeToTheJungle.getAnimals().get(i).getX() == selection.getKey() && welcomeToTheJungle.getAnimals().get(i).getY() == selection.getValue()) {
                    selectedAnimal = welcomeToTheJungle.getAnimals().get(i);
                    break;
                }
            }
        });
    }

    /**
     * Summon Conrad Kellogg. He will handle the rest.
     */
    public void killAnimal(){
        selectedAnimal.setHealth(0);
    }
    /**
     * Reroute the animal to previously selected position.
     */
    public void rerouteAnimal(){
        if (selectedAnimal instanceof Prey) {
            ((Prey) selectedAnimal).reroute(selection.getKey(), selection.getValue());
        }
    }
    /**
     * Start/stop button handler.
     */
    public void start(){
        if (startButton.getText().equals("START")){
            startButton.setText("STOP");
            welcomeToTheJungle.run();
        }
        else{
            startButton.setText("START");
            welcomeToTheJungle.stop();
        }
    }

    /**
     * Add prey button handler.
     */
    public void addPrey(){
        if (animalTextField.getText().equals("")){
            //glow border red
            animalTextField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }
        else{
            welcomeToTheJungle.addPrey(animalTextField.getText());
            animalTextField.setText("");
            //return to normal
            animalTextField.setStyle("-fx-border-color: transparent ; -fx-border-width: 0px ;");
        }

    }

    /**
     * Add predator button handler.
     */
    public void addPredator(){
        if (animalTextField.getText().equals("")){
            //glow border red
            animalTextField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }
        else{
            welcomeToTheJungle.addPredator(animalTextField.getText());
            animalTextField.setText("");
            //return to normal
            animalTextField.setStyle("-fx-border-color: transparent ; -fx-border-width: 0px ;");
        }
    }
}
