package com.oopproject.world.map.locations;

import com.oopproject.world.animals.Prey;

/**
 * Class representing hideout, a spot safe from predators. The Vault Tec doesn't want you to know about this, but it is
 * a social experiment and the whole nuclear war was just a cover up.
 */
public class Hideout extends Location {
    /**
     * Get a string representation of the object.
     * @return the string representation
     */
    public String toString() {
        return super.toString() + "This is a hideout.\n";
    }

    /**
     * Implemenazione del metodo from (was too lazy to translate this) inserebile per la classe Hideout - spaghetti (hope not my code) all'Amatriciana.
     * @param entering prey entering the location
     */
    @Override
    public synchronized void enter(Prey entering) {
        super.enter(entering);
        entering.setHidden(true);
    }

    /**
     * Implemenazione del metodo from (same as above, sorry) inserebile per la classe Hideout - tagliatelle con bolognese.
     * @param leaving
     */
    @Override
    public synchronized void leave(Prey leaving) {
        super.leave(leaving);
        leaving.setHidden(false);
    }

    /**
     * Constructor for the hideout.
     * @param x x coordinate of the hideout
     * @param y y coordinate of the hideout
     * @param name name of the hideout
     * @param maxInside maximum number of animals that can be inside the hideout
     */
    public Hideout(int x, int y, String name, int maxInside) {
        super(x, y, name, maxInside);
    }


}
