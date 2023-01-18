package com.oopproject.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import com.oopproject.world.animals.*;
import com.oopproject.world.locations.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class Mapper {
    private double x_max = 0;
    private double y_max = 0;
    private double x_step = 0;
    private double y_step = 0;
    private double canvas_size = 0;
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

    Mapper(Canvas mapCanvas, double canvas_size){
        // initialize mapper
        this.mapCanvas = mapCanvas;
        mapCanvas.setHeight(canvas_size);
        mapCanvas.setWidth(canvas_size);
        x_max = (int) canvas_size;
        y_max = (int) canvas_size;
        x_step = x_max / 23;
        y_step = y_max / 23;
        System.out.println("Mapper created");
        this.pathImage = new Image("file:src/main/resources/images/path.png");
        this.crossroadImage = new Image("file:src/main/resources/images/crossroad.png");
        this.dirtImage = new Image("file:src/main/resources/images/dirt.png");
        this.foodImage = new Image("file:src/main/resources/images/food.png");
        this.hideoutImage = new Image("file:src/main/resources/images/hideout.png");
        this.preyImage = new Image("file:src/main/resources/images/prey.png");
        this.predatorImage = new Image("file:src/main/resources/images/predator.png");
        this.waterImage = new Image("file:src/main/resources/images/water.png");
        baseCanvas = new Canvas(canvas_size, canvas_size);
        animalCanvas = new Canvas(canvas_size, canvas_size);
    }
    public void drawMap(HashMap<Pair<Integer, Integer>, Location> map_locations) {
        System.out.println("Drawing map");
        for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 23; j++) {
                if (map_locations.containsKey(new Pair<>(i, j))) {
                    Location location = map_locations.get(new Pair<>(i, j));
                    mapCanvas.getGraphicsContext2D().drawImage(pathImage, i * x_step, j * y_step, x_step, y_step);
                    if (location instanceof Path && location.getMax_inside() == 1) {
                        mapCanvas.getGraphicsContext2D().drawImage(crossroadImage, i * x_step, j * y_step, x_step, y_step);
                    } else if (location instanceof WaterSource) {
                        mapCanvas.getGraphicsContext2D().drawImage(waterImage, i * x_step, j * y_step, x_step, y_step);
                    } else if (location instanceof FoodSource) {
                        mapCanvas.getGraphicsContext2D().drawImage(foodImage, i * x_step, j * y_step, x_step, y_step);
                    } else if (location instanceof Hideout) {
                        mapCanvas.getGraphicsContext2D().drawImage(hideoutImage, i * x_step, j * y_step, x_step, y_step);
                    }
                } else {
                    mapCanvas.getGraphicsContext2D().drawImage(dirtImage, i * x_step, j * y_step, x_step, y_step);
                }
            }
        }
        //copy mapCanvas to baseCanvas (image)
        baseCanvas.getGraphicsContext2D().drawImage(mapCanvas.getGraphicsContext2D().getCanvas().snapshot(null, null), 0, 0);
    }
    public void drawAnimals(ArrayList<Animal> animals) {
        animalCanvas.getGraphicsContext2D().drawImage(baseCanvas.snapshot(null, null), 0, 0);
        for (Animal animal : animals) {
            if (animal instanceof Prey) {
                animalCanvas.getGraphicsContext2D().drawImage(preyImage, animal.getX() * x_step, animal.getY() * y_step, x_step, y_step);
            } else if (animal instanceof Predator) {
                animalCanvas.getGraphicsContext2D().drawImage(predatorImage, animal.getX() * x_step, animal.getY() * y_step, x_step, y_step);
            }
        }
        mapCanvas.getGraphicsContext2D().drawImage(baseCanvas.getGraphicsContext2D().getCanvas().snapshot(null, null), 0, 0);
        mapCanvas.getGraphicsContext2D().drawImage(animalCanvas.getGraphicsContext2D().getCanvas().snapshot(null, null), 0, 0);
    }

}
