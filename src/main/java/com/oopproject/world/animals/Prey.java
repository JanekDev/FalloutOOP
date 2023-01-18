package com.oopproject.world.animals;

import com.oopproject.world.Map;
import com.oopproject.world.locations.Hideout;
import com.oopproject.world.locations.Location;
import com.oopproject.world.locations.Source;
import com.oopproject.world.locations.WaterSource;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class representing prey that drinks water and eats plants. It is also hunted by predators.
 * @author michal
 */
public class Prey extends Animal{
    private int energy_level;
    private int water_level;
    private Map map;
    private ArrayList<Animal> animals;


    @Override
    public void step() {
        Location current_location = map.getLocation(getX(), getY());
        if (current_location instanceof Hideout) {

        }
        else if (current_location instanceof WaterSource) {
            water_level = 100;
        }
        else if (current_location instanceof Source) {
            energy_level = 100;
        }
        else {
            water_level -= 10;
            energy_level -= 10;
        }
    }
    public Location findLocation (String type){
        switch (type) {
            case "WaterSource":
                for (int i = 0; i < 10; i++) {
                    int x = (int) (Math.random() * 22);
                    int y = (int) (Math.random() * 22);
                    Location current_location = map.getLocation(x, y);
                    if (current_location instanceof WaterSource) {
                        return current_location;
                    }
                }
                return null;
            case "Source":
                for (int i = 0; i < 10; i++) {
                    int x = (int) (Math.random() * 22);
                    int y = (int) (Math.random() * 22);
                    Location current_location = map.getLocation(x, y);
                    if (current_location instanceof Source) {
                        return current_location;
                    }
                }
                return null;
            case "Hideout":
                for (int i = 0; i < 10; i++) {
                    int x = (int) (Math.random() * 22);
                    int y = (int) (Math.random() * 22);
                    Location current_location = map.getLocation(x, y);
                    if (current_location instanceof Hideout) {
                        return current_location;
                    }
                }
                return null;
        }
        return null;
    }
    @Override
    public void move(int x, int y) {
        // check what is on the new location
        Location new_location = map.getLocation(this.getX() + x, this.getY() + y);
        Location current_location = map.getLocation(this.getX(), this.getY());
        current_location.leave(this);
        new_location.enter(this);
        super.move(x, y);
    }
    @Override
    public void run(){
        super.run();
        map.getLocation(this.getX(), this.getY()).leave(this);
    }

    /**
     *
     * @param energy_level
     * @param water_level
     * @param name
     * @param health
     * @param speed
     * @param strength
     * @param species_name
     */
    public Prey(int x, int y, int energy_level, int water_level, String name, int health, int speed, int strength, String species_name, Map map, ArrayList<Animal> animals) {
        super(x, y, name, health, speed, strength, species_name, animals);
        this.energy_level = energy_level;
        this.water_level = water_level;
        this.map = map;
        this.animals = animals;
    }

    /**
     * @return the energy_level
     */
    public int getEnergy_level() {
        return energy_level;
    }

    /**
     * @param energy_level the energy_level to set
     */
    public void setEnergy_level(int energy_level) {
        this.energy_level = energy_level;
    }

    /**
     * @return the water_level
     */
    public int getWater_level() {
        return water_level;
    }

    /**
     * @param water_level the water_level to set
     */
    public void setWater_level(int water_level) {
        this.water_level = water_level;
    }

    public void drinkWater(Source source){

    }
    /**
     * Method for prey to eat the plants.
     * @param source the food source to be eaten
     */
    public void eatPlant(Source source){

    }

    /**
     * Method for prey to reproduce.
     */
    public void reproduce(){

    }

    /**
     * Method for prey to return to hideout.
     */
    public void returnToHideout(){

    }

    /**
     * Method for prey to find source of food or water.
     */
    public void findSource(){

    }

    /**
     * Method for prey to die.
     */
    public void die(){

    }

}

