package com.oopproject.world.factories;

import com.oopproject.world.animals.*;
import com.oopproject.world.locations.*;
public class LocationFactory {
    /**
     *
     * @param type
     * @param x
     * @param y
     * @return
     */
    // set the math random to some value to test
    public static Location createLocation(String type, int x, int y) {
        int replenishment = (int) (Math.random() * 10) + 11;
        int max_inside = (int) (Math.random() * 7) + 1;
        // name is a randomized string of length 5
        String name = "";
        for (int i = 0; i < 5; i++) {
            name += (char) ((int) (Math.random() * 26) + 97);
        }
        switch (type) {
            case "Path":
                return new Path(x, y, 1000);
            case "Water":
                return new WaterSource(x, y, name, replenishment, max_inside);
            case "Food":
                return new FoodSource(x, y, name, replenishment, max_inside);
            case "Hideout":
                return new Hideout(x, y, name, max_inside);
            default:
                return null;
        }
    }
}
