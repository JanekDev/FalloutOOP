package com.oopproject.world.map.locations;

/**
 * This class represents a food source. Yummy Sugar Bombs! Not causing diabetes or cancer, I swear!
 */
public class FoodSource extends Source {
    /**
     * Get a string representation of the object.
     * @return the string representation
     */
    public String toString() {
        return super.toString() + "This is a food source.\n";
    }
    /**
     * Constructor for the food source.
     * @param x x coordinate of the food source
     * @param y y coordinate of the food source
     * @param name name of the food source
     * @param preyReplenishmentSpeed replenishment speed of the food source
     * @param maxInside maximum number of animals that can be inside the food source
     */
    public FoodSource(int x, int y, String name, int preyReplenishmentSpeed, int maxInside) {
        super(x, y, name, preyReplenishmentSpeed, maxInside);
    }
}
