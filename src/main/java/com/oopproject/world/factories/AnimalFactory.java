package com.oopproject.world.factories;

import com.oopproject.world.map.Map;
import com.oopproject.world.animals.*;
import com.oopproject.world.map.locations.Hideout;
import com.oopproject.world.map.locations.Location;
import java.util.ArrayList;

/**
 * A factory class that creates animals. Or humans. Or super mutants. Or whatever moves.
 */
public class AnimalFactory {
    private Map map;
    private ArrayList<Animal> animals;
    /**
     * Creates an animal with the given name and type.
     * @param name name of the animal
     * @param type type of the animal
     * @return the created animal if successful, null otherwise
     */
    public Animal createAnimal(String name, String type) {
        int energyLevel = 100;
        int waterLevel = 100;
        int health = 100;
        int speed = (int) (Math.random() * 61) + 1;
        int strength = (int) (Math.random() * 61) + 1;
        int x = (int) (Math.random() * 22);
        int y = (int) (Math.random() * 22);
        switch (type) {
            case "Prey":

                Location currentLocation = map.getLocation(x, y);

                for(int i = 0; i < 10; i++) {
                    while (!(currentLocation instanceof Hideout)) {
                        x = (int) (Math.random() * 22);
                        y = (int) (Math.random() * 22);
                        currentLocation = map.getLocation(x, y);
                    }
                    if (currentLocation instanceof Hideout && currentLocation.getPreyInside().size() < currentLocation.getMaxInside()) {
                        Prey prey = new Prey(x, y, energyLevel, waterLevel, name, health, speed, strength, map, animals);
                        currentLocation.enter(prey);
                        return prey;
                    }
                }
                return null;
            case "Predator":
                speed = (int) (Math.random() * 61) + 40;
                strength = (int) (Math.random() * 61) + 40;
                return new Predator(x, y, name, 100, speed, strength, animals);
            default:
                return null;
        }
    }

    /**
     * Constructor for the AnimalFactory class.
     * @param map map object
     * @param animals list of animals
     */
    public AnimalFactory (Map map, ArrayList<Animal> animals) {
        this.map = map;
        this.animals = animals;
    }
}
