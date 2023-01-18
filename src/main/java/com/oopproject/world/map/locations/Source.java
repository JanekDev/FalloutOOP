package com.oopproject.world.map.locations;

/**
 * Class that represents supply source for prey. Or people. Or both. You can never be sure.
 * The supplies that you can replenish here are always healthy and tasty. No radiation here!
 */
public abstract class Source extends Location {
    private int preyReplenishmentSpeed;
    public String toString() {
        return super.toString() + "Prey replenishment speed: " + preyReplenishmentSpeed + "\n";
    }
    /**
     * Constructor for the source.
     * @param x x coordinate of the source
     * @param y y coordinate of the source
     * @param name name of the source
     * @param setPreyReplenishmentSpeed replenishment speed of the source
     * @param maxInside maximum number of animals that can be inside the source
     */
    public Source(int x, int y, String name, int setPreyReplenishmentSpeed, int maxInside) {
        super(x, y, name, maxInside);
        this.setPreyReplenishmentSpeed(setPreyReplenishmentSpeed);
    }
    /**
     * Getter for the replenishment speed.
     * @return the preyReplenishmentSpeed
     */
    public int getPreyReplenishmentSpeed() {
        return preyReplenishmentSpeed;
    }

    /**
     * Setter for the replenishment speed.
     * @param preyReplenishmentSpeed the preyReplenishmentSpeed to set
     */
    public void setPreyReplenishmentSpeed(int preyReplenishmentSpeed) {
        this.preyReplenishmentSpeed = preyReplenishmentSpeed;
    }
}

