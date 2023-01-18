package com.oopproject.world.map.locations;

/**
 * This class represents a walkable path tile. It is used to connect the locations. The road needs some renovation, but
 * the classic diners are still there.
 */
public class Path extends Location {

    public String toString() {
        return super.toString() + "This is a path.\n";
    }
    /**
     * Constructor for the path.
     * @param x x coordinate of the path
     * @param y y coordinate of the path
     * @param maxInside maximum number of animals that can be inside the path
     */
    public Path(int x, int y, int maxInside) {
        super(x, y, "Route 66", maxInside); // if 1 it is a crossroad
    }
}
