package com.oopproject.world.factories;

import com.oopproject.world.Map;
import com.oopproject.world.animals.*;
import com.oopproject.world.locations.Hideout;
import com.oopproject.world.locations.Location;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class AnimalFactory {
    private Map map;
    private ArrayList<Animal> animals;
    /**
     *
     * @param name
     * @param type
     * @return
     */
    public Animal createAnimal(String name, String type) {
        // todo add more types
        int energy_level = 100;
        int water_level = 100;
        int health = 100;
        int speed = (int) (Math.random() * 61);
        int strength = (int) (Math.random() * 61);
        int x = (int) (Math.random() * 22);
        int y = (int) (Math.random() * 22);
        switch (type) {
            case "Prey":

                Location current_location = map.getLocation(x, y);

                for(int i = 0; i < 10; i++) {
                    while (!(current_location instanceof Hideout)) {
                        x = (int) (Math.random() * 22);
                        y = (int) (Math.random() * 22);
                        current_location = map.getLocation(x, y);
                    }
                    if (current_location instanceof Hideout && current_location.getPrey_inside().size() < current_location.getMax_inside()) {
                        Prey prey = new Prey(x, y, energy_level, water_level, name, health, speed, strength, type, map, animals);
                        current_location.enter(prey);
                        return prey;
                    }
                }
                return null;
            case "Predator":
                speed = (int) (Math.random() * 61) + 40;
                strength = (int) (Math.random() * 61) + 40;
                return new Predator(x, y, name, 100, speed, strength, type, animals);
            default:
                return null;
        }
    }
    public AnimalFactory (Map map, ArrayList<Animal> animals) {
        this.map = map;
        this.animals = animals;
    }
}
