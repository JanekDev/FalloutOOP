package com.oopproject.world.animals;
import com.oopproject.world.MapObject;
import com.oopproject.world.interfaces.Movable;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Base class for predators and animals, allowing movement.
 * @author michal
 */
public abstract class Animal extends MapObject implements Movable, Runnable {
    private int destination_x;
    private int destination_y;
    private ArrayList<Animal> animals;

    public void step(){
        throw new UnsupportedOperationException("Not working for high level class");
    }

    public void run(){
        while (getHealth() > 0) {
            if (this.isRunning()) {
                step();
            }
            try {
                sleep((1000 / this.getSpeed())*100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("dying");
        getAnimals().remove(this);
    }

    @Override
    public void move(int x, int y) {
        // move to x,y
        if (this.getX() + x < 0 || this.getX() + x > 22 || this.getY() + y < 0 || this.getY() + y > 22) {
            // out of bounds
            return;
        }
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
    }

    /**
     *
     * @param name
     * @param health
     * @param speed
     * @param strength
     * @param species_name
     */
    public Animal(int x, int y, String name, int health, int speed, int strength, String species_name, ArrayList<Animal> animals) {
        super(x, y);
        this.setName(name);
        this.setHealth(health);
        this.setSpeed(speed);
        this.setStrength(strength);
        this.setSpecies_name(species_name);
        this.setAnimals(animals);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * @return the strength
     */
    public int getStrength() {
        return strength;
    }

    /**
     * @param strength the strength to set
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * @return the species_name
     */
    public String getSpecies_name() {
        return species_name;
    }

    /**
     * @param species_name the species_name to set
     */
    public void setSpecies_name(String species_name) {
        this.species_name = species_name;
    }

    private String name;
    private int health;
    private int speed;
    private int strength;
    private String species_name;
    private boolean running = false;

    public int getDestination_x() {
        return destination_x;
    }

    public void setDestination_x(int destination_x) {
        this.destination_x = destination_x;
    }

    public int getDestination_y() {
        return destination_y;
    }

    public void setDestination_y(int destination_y) {
        this.destination_y = destination_y;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }
}
