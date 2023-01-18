package com.oopproject.world;

import com.oopproject.gui.Mapper;
import com.oopproject.world.animals.Animal;
import com.oopproject.world.animals.Predator;
import com.oopproject.world.animals.Prey;
import com.oopproject.world.factories.AnimalFactory;
import com.oopproject.world.map.Map;
import javafx.animation.AnimationTimer;
import java.util.ArrayList;

/**
 * A singleton class that represents the world.
 */
public class World {
    private Mapper mapper;
    private AnimalFactory animalFactory;
    private ArrayList<Animal> animals;
    private Map map;
    private boolean running = false;
    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            step();
        }
    };

    /**
     * Get the information about the object.
     * @param x x coordinate of the object
     * @param y y coordinate of the object
     * @return the information about the object
     */
    public String getObjectInfo(int x, int y) {
        for (Animal animal : getAnimals()) {
            if (animal.getX() == x && animal.getY() == y) {
                return animal.toString();
            }
        }
        if (map.getLocation(x, y) != null) {
            return map.getLocation(x, y).toString();
        }
        else {
            return "Nothing here, only dirt.";
        }
    }

    /**
     * Constructor
     * @param mapper - mapper object, used in drawing the map
     */
    public World(Mapper mapper){
        this.setMapper(mapper);
        this.setAnimals(new ArrayList<>());
        this.map = new Map();
        System.out.println("World created");
        mapper.drawMap(this.map.getLocationHashMap());
        this.animalFactory = new AnimalFactory(map, getAnimals());
    }

    /**
     * Adds prey to the map at the hideout.
     * @param name name of the prey
     */
    public void addPrey(String name){
        Prey p = (Prey) this.animalFactory.createAnimal(name, "Prey");
        if (p != null){
            p.setRunning(running);
            this.getAnimals().add(p);
            new Thread(p).start();
            System.out.println("Prey " + name + " added");
            refreshMap();
        } else {
            System.out.println("Prey " + name + " not added");
        }

    }

    /**
     * Adds predator to the map at a random location.
     * @param name name of the predator
     */
    public void addPredator(String name){
        Predator p = (Predator) this.animalFactory.createAnimal(name, "Predator");
        p.setRunning(running);
        this.getAnimals().add(p);
        new Thread(p).start();
        System.out.println("Predator " + name + " added");
        refreshMap();
    }
    /**
     * Runs the whole simulation
     */
    public void run(){
        running = true;
        for (int i = 0; i < this.getAnimals().size(); i++){
            this.getAnimals().get(i).setRunning(true);
        }
        timer.start();
    }

    /**
     * Pauses the simulation
     */
    public void stop(){
        running = false;
        for (int i = 0; i < this.getAnimals().size(); i++){
            this.getAnimals().get(i).setRunning(false);
        }
        timer.stop();
    }

    /**
     * Refreshes the map
     */
    public void refreshMap(){
        this.mapper.drawAnimals(getAnimals());
    }

    /**
     * Makes one time step for the main drawing thread
     */
    public void step(){
            refreshMap();
    }

    /**
     * Getter for the mapper
     * @return mapper to handle drawing
     */
    public Mapper getMapper() {
        return mapper;
    }

    /**
     * Mapper setter
     * @param mapper mapper to handle drawing
     */
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Checks if the simulation is running
     * @return
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Sets the running state of the simulation
     * @param running
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Getter for the animals
     * @return list of animals
     */
    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    /**
     * Setter for the animals
     * @param animals list of animals
     */
    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }
}
