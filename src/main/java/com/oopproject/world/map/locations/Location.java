package com.oopproject.world.map.locations;

import com.oopproject.world.map.MapObject;
import com.oopproject.world.animals.Prey;
import com.oopproject.world.interfaces.Enterable;
import java.util.ArrayList;

/**
 * Base class for any location on the map. It is enterable or as we should say inseribile.
 */
public abstract class Location extends MapObject implements Enterable {
    private String name;
    private int maxInside;
    private ArrayList<Prey> preyInside;

    /**
     * Get a string representation of the object.
     * @return the string representation
     */
    public String toString() {
        return super.toString() + "Name: " + name + "\n" + "Max inside: " + maxInside + "\n" + "Prey inside: " + preyInside.size() + "\n";
    }

    /**
     * Implementation of the enter method from the Enterable interface - pappardele alla arrabbiata.
     * @param entering prey entering the location
     */
    @Override
    public synchronized void enter(Prey entering) {
        if (preyInside.size() < maxInside) {
            preyInside.add(entering);
        }
    }

    /**
     * Implementation of the leave method from the Enterable interface - gniocchi alla sorrentina.
     * @param leaving
     */
    @Override
    public synchronized void leave(Prey leaving) {
        preyInside.remove(leaving);
    }


    /**
     * Constructor for the location.
     * @param name name of the location
     * @param maxInside maximum number of prey that can be inside
     */
    public Location(int x, int y, String name, int maxInside) {
        super(x,y);
        this.setName(name);
        this.setMaxInside(maxInside);
        this.setPreyInside(new ArrayList<>());
    }

    /**
     * Getter for the name of the location.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the location.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the maximum number of prey that can be inside the location.
     * @return the maxInside
     */
    public int getMaxInside() {
        return maxInside;
    }

    /**
     * Setter for the maximum number of prey that can be inside the location.
     * @param maxInside the maxInside to set
     */
    public void setMaxInside(int maxInside) {
        this.maxInside = maxInside;
    }

    /**
     * Getter for the prey inside the location.
     * @return the preyInside
     */
    public ArrayList<Prey> getPreyInside() {
        return preyInside;
    }

    /**
     * Setter for the prey inside the location.
     * @param preyInside the preyInside to set
     */
    public void setPreyInside(ArrayList<Prey> preyInside) {
        this.preyInside = preyInside;
    }
}
