package com.oopproject.world;

import com.oopproject.world.locations.Location;
import javafx.util.Pair;

import java.util.HashMap;

public class Map {
    public Map(HashMap<Pair<Integer, Integer>, Location> map_locations) {
        this.setMap_locations(map_locations);
    }

    public Location getLocation(int x, int y) {
        return getMap_locations().get(new Pair<>(x, y));
    }

    public void setLocation(int x, int y, Location location) {
        getMap_locations().put(new Pair<>(x, y), location);
    }

    private HashMap<Pair<Integer, Integer>, Location> map_locations;

    public HashMap<Pair<Integer, Integer>, Location> getMap_locations() {
        return map_locations;
    }

    public void setMap_locations(HashMap<Pair<Integer, Integer>, Location> map_locations) {
        this.map_locations = map_locations;
    }
}
