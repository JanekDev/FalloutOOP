package com.oopproject.world.map.locations;

/**
 * This class represents a water source. It is a source of water, but only place where nowadays you can find a
 * potable water are Nuka Cola vending machines. With a bit of luck, you can find a Special Quantum Edition!
 */
public class WaterSource extends Source {
    public String toString() {
        return super.toString() + "This is a water source.\n";
    }
    /**
     * Constructor for the water source.
     * @param x x coordinate of the water source
     * @param y y coordinate of the water source
     * @param name name of the water source
     * @param preyReplenishmentSpeed replenishment speed of the water source
     * @param maxInside maximum number of animals that can be inside the water source
     */
    public WaterSource(int x, int y, String name, int preyReplenishmentSpeed, int maxInside) {
        super(x, y, name, preyReplenishmentSpeed, maxInside);
    }
}
