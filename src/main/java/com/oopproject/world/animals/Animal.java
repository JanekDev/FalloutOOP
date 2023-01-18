package com.oopproject.world.animals;

import com.oopproject.world.map.MapObject;
import com.oopproject.world.interfaces.Movable;
import java.util.ArrayList;
import static java.lang.Thread.sleep;

/**
 * Base class for predators and animals, allowing movement.
 */
public abstract class Animal extends MapObject implements Movable, Runnable {
    private ArrayList<Animal> animals;
    private String name;
    private int health;
    private int speed;
    private int strength;
    private boolean running = false;
    private String species;
    public String toString() {
        return super.toString() + "Name: " + name + "\n" + "Health: " + health + "\n" + "Speed: " + speed + "\n" + "Strength: " + strength + "\n" + "Species: " + species + "\n";
    }

    /**
     * Step method for animal, allowing movement, will be implemented in subclasses.
     */
    public void step(){
        throw new UnsupportedOperationException("Not working for high level class");
    }

    /**
     * Run method that each time checks if animal is running and alive, if so, calls step method.
     */
    public void run(){
        while (getHealth() > 0) {
            if (this.isRunning()) {
                step();
            }
            try {
                sleep((600 / this.getSpeed())*100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(this.getName() + " died" + (this instanceof Predator ? " as a predator" : " as a prey"));
        getAnimals().remove(this);
    }

    /**
     * Implementation of Movable (mobile) interface, allowing movement - implementazione pizza pasta.
     * @param x x offset
     * @param y y offset
     */
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
     * Constructor for animal.
     * @param name the name of animal
     * @param health the health of animal (0-100)
     * @param speed the speed of animal (0-100)
     * @param strength the strength of animal (0-100)
     */
    public Animal(int x, int y, String name, int health, int speed, int strength, ArrayList<Animal> animals, String species) {
        super(x, y);
        this.setName(name);
        this.setHealth(health);
        this.setSpeed(speed);
        this.setStrength(strength);
        this.setAnimals(animals);
        this.setSpecies(species);
    }

    /**
     * The name getter.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * The name setter.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The health getter.
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * The health setter.
     * @param health the health to set
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * The speed getter.
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * The speed setter.
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * The strength getter.
     * @return the strength
     */
    public int getStrength() {
        return strength;
    }

    /**
     * The strength setter.
     * @param strength the strength to set
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * The running getter.
     * @return the running state
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * The running setter.
     * @param running the running state to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * The animals getter.
     * @return the animals
     */
    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    /**
     * The animals setter.
     * @param animals the animals to set
     */
    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}
