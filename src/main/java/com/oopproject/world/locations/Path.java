package com.oopproject.world.locations;
import com.oopproject.world.animals.*;

public class Path extends Location {
    public Path(int x, int y, int max_inside) {
        super(x, y, "Route 60", max_inside); // if 1 it is a crossroad
    }
}
