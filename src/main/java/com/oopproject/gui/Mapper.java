package com.oopproject.gui;

import com.oopproject.world.map.locations.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import com.oopproject.world.animals.*;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Class used for drawing the map. The Brotherhood of Steel is here to help you map the brave new, post-apocalyptic world.
 */
public class Mapper {
    private double xMax = 0;
    private double yMax = 0;
    private double xStep = 0;
    private double yStep = 0;
    private Canvas mapCanvas;
    private Canvas baseCanvas;
    private Canvas animalCanvas;
    private Image pathImage;
    private Image crossroadImage;
    private Image preyImage;
    private Image foodImage;
    private Image hideoutImage;
    private Image predatorImage;
    private Image waterImage;
    private Image dirtImage;
    private static final int MAP_SIZE = 23;

    /**
     * Draws the map on the canvas, using the locationHashMap HashMap
     * @param locationHashMap - a HashMap containing all the locations
     */
    public void drawMap(HashMap<Pair<Integer, Integer>, Location> locationHashMap) {
        System.out.println("Drawing map");
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (locationHashMap.containsKey(new Pair<>(i, j))) {
                    Location location = locationHashMap.get(new Pair<>(i, j));
                    mapCanvas.getGraphicsContext2D().drawImage(pathImage, i * getxStep(), j * getyStep(), getxStep(), getyStep());
                    if (location instanceof Path && location.getMaxInside() == 1) {
                        mapCanvas.getGraphicsContext2D().drawImage(crossroadImage, i * getxStep(), j * getyStep(), getxStep(), getyStep());
                    } else if (location instanceof WaterSource) {
                        mapCanvas.getGraphicsContext2D().drawImage(waterImage, i * getxStep(), j * getyStep(), getxStep(), getyStep());
                    } else if (location instanceof FoodSource) {
                        mapCanvas.getGraphicsContext2D().drawImage(foodImage, i * getxStep(), j * getyStep(), getxStep(), getyStep());
                    } else if (location instanceof Hideout) {
                        mapCanvas.getGraphicsContext2D().drawImage(hideoutImage, i * getxStep(), j * getyStep(), getxStep(), getyStep());
                    }
                } else {
                    mapCanvas.getGraphicsContext2D().drawImage(dirtImage, i * getxStep(), j * getyStep(), getxStep(), getyStep());
                }
            }
        }
        //copy mapCanvas to baseCanvas (image)
        baseCanvas.getGraphicsContext2D().drawImage(mapCanvas.getGraphicsContext2D().getCanvas().snapshot(null, null), 0, 0);
    }

    /**
     * Draws the animals on the map
     * @param animals - an ArrayList containing all the animals
     */
    public void drawAnimals(ArrayList<Animal> animals) {
        animalCanvas.getGraphicsContext2D().drawImage(baseCanvas.snapshot(null, null), 0, 0);
        for (Animal animal : animals) {
            if (animal instanceof Prey) {
                animalCanvas.getGraphicsContext2D().drawImage(preyImage, animal.getX() * getxStep(), animal.getY() * getyStep(), getxStep(), getyStep());
            } else if (animal instanceof Predator) {
                animalCanvas.getGraphicsContext2D().drawImage(predatorImage, animal.getX() * getxStep(), animal.getY() * getyStep(), getxStep(), getyStep());
            }
        }
        mapCanvas.getGraphicsContext2D().drawImage(baseCanvas.getGraphicsContext2D().getCanvas().snapshot(null, null), 0, 0);
        mapCanvas.getGraphicsContext2D().drawImage(animalCanvas.getGraphicsContext2D().getCanvas().snapshot(null, null), 0, 0);
    }
    /**
     * Constructor for the Mapper class
     * @param mapCanvas - the canvas on which the map is drawn, from the controller of main window
     * @param canvasSize - the size of the canvas
     */
    Mapper(Canvas mapCanvas, double canvasSize){
        // initialize mapper
        this.mapCanvas = mapCanvas;
        mapCanvas.setHeight(canvasSize);
        mapCanvas.setWidth(canvasSize);
        xMax = (int) canvasSize;
        yMax = (int) canvasSize;
        setxStep(xMax / MAP_SIZE);
        setyStep(yMax / MAP_SIZE);
        System.out.println("Mapper created");
        this.pathImage = new Image("file:src/main/resources/images/path.png");
        this.crossroadImage = new Image("file:src/main/resources/images/crossroad.png");
        this.dirtImage = new Image("file:src/main/resources/images/dirt.png");
        this.foodImage = new Image("file:src/main/resources/images/food.png");
        this.hideoutImage = new Image("file:src/main/resources/images/hideout.png");
        this.preyImage = new Image("file:src/main/resources/images/prey.png");
        this.predatorImage = new Image("file:src/main/resources/images/predator.png");
        this.waterImage = new Image("file:src/main/resources/images/water.png");
        baseCanvas = new Canvas(canvasSize, canvasSize);
        animalCanvas = new Canvas(canvasSize, canvasSize);
    }

    /**
     * Getter for the xStep variable
     * @return xStep
     */
    public double getxStep() {
        return xStep;
    }

    /**
     * Setter for the xStep variable
     * @param xStep - the new value of xStep
     */
    public void setxStep(double xStep) {
        this.xStep = xStep;
    }

    /**
     * Getter for the yStep variable
     * @return yStep
     */
    public double getyStep() {
        return yStep;
    }

    /**
     * Setter for the yStep variable
     * @param yStep - the new value of yStep
     */
    public void setyStep(double yStep) {
        this.yStep = yStep;
    }
}
