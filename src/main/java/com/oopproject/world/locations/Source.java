package com.oopproject.world.locations;

/**
 * Class that represents supply source for prey.
 * @author michal
 */
public abstract class Source extends Location {
    /**
     * @return the prey_replenishment_speed
     */
    public int getPrey_replenishment_speed() {
        return prey_replenishment_speed;
    }

    /**
     * @param prey_replenishment_speed the prey_replenishment_speed to set
     */
    public void setPrey_replenishment_speed(int prey_replenishment_speed) {
        this.prey_replenishment_speed = prey_replenishment_speed;
    }

    /**
     * @param x
     * @param y
     * @param name
     * @param prey_replenishment_speed
     * @param max_inside
     */
    public Source(int x, int y, String name, int prey_replenishment_speed, int max_inside) {
        super(x, y, name, max_inside);
        this.setPrey_replenishment_speed(prey_replenishment_speed);
    }

    private int prey_replenishment_speed;
    private String type;

}

