package com.oopproject.world.map;

/**
 * This class represents a map object.
 */
public abstract class MapObject {
    private int x;
    private int y;

    /**
     * Get the string representation of the object.
     * @return the string representation
     */
    public String toString() {
        return "Position: (" + x + ", " + y + ")\n";
    }
    /**
     * This constructor creates a map object.
     * @param x x coordinate of the map object
     * @param y y coordinate of the map object
     */
    public MapObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for the x coordinate.
     * @return x coordinate of the map object
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for the y coordinate.
     * @param x x coordinate of the map object
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter for the y coordinate.
     * @return y coordinate of the map object
     */
    public int getY() {
        return y;
    }

    /**
     * Setter for the y coordinate.
     * @param y y coordinate of the map object
     */
    public void setY(int y) {
        this.y = y;
    }
}
