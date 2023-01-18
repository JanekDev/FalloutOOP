package com.oopproject.world.locations;

import com.oopproject.world.animals.Prey;
import java.util.ArrayList;
import java.util.Queue;

/**
 * Class representing hideout, a spot safe from predators.
 * @author michal
 */
public class Hideout extends Location {

    /**
     *
     * @param x
     * @param y
     * @param name
     * @param max_inside
     */
    public Hideout(int x, int y, String name, int max_inside) {
        super(x, y, name, max_inside);
    }

}
