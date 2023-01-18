package com.oopproject.world.factories;

import com.oopproject.world.map.locations.*;

/**
 * The class used to create the locations. Est. 2077.
 */
public class LocationFactory {
    private static final int MAX_INSIDE_PATH = 1000;
    /**
     * Creates a location object based on the type of the location.
     * @param type type of the location
     * @param x x coordinate of the location
     * @param y y coordinate of the location
     * @return the created location if successful, null otherwise
     */
    public static Location createLocation(String type, int x, int y) {
        int replenishment = (int) (Math.random() * 10) + 1;
        int maxInside = (int) (Math.random() * 7) + 1;
        switch (type) {
            case "Path":
                return new Path(x, y, MAX_INSIDE_PATH);
            case "Water":
                return new WaterSource(x, y, "Nuka Cola Vending Machine", replenishment, maxInside);
            case "Food":
                return new FoodSource(x, y, "Sugar Bombs", replenishment, maxInside);
            case "Hideout":
                return new Hideout(x, y, "Vault 111", maxInside);
            default:
                return null;
        }
    }
}
