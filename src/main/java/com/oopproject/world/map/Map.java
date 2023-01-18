package com.oopproject.world.map;

import com.oopproject.world.factories.LocationFactory;
import com.oopproject.world.map.locations.Hideout;
import com.oopproject.world.map.locations.Location;
import com.oopproject.world.map.locations.Path;
import com.oopproject.world.map.locations.Source;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The map created by the Brotherhood of Steel. It contains all the locations and the paths between them.
 */
public class Map {
    private HashMap<Pair<Integer, Integer>, Location> locationHashMap;
    private LocationFactory locationFactory;
    private Pathfinder pathfinder;

    /**
     * Creates locations using the LocationFactory and adds them to the map.
     * @return the location hashmap
     */
    private HashMap<Pair<Integer, Integer>, Location> createLocations() {
        int waterCount = 0;
        int foodCount = 0;
        int hideoutCount = 0;
        HashMap<Pair<Integer, Integer>, Location> mapLocations = new HashMap<>();
        while (waterCount < 10 || foodCount < 10 || hideoutCount < 5){
            Location food = this.locationFactory.createLocation("Food", (int) (Math.random() * 22), (int) (Math.random() * 22));
            Location water = this.locationFactory.createLocation("Water", (int) (Math.random() * 22), (int) (Math.random() * 22));
            if(!mapLocations.containsKey(new Pair<>(food.getX(), food.getY())) && foodCount < 10){
                mapLocations.put(new Pair<>(food.getX(), food.getY()), food);
                foodCount++;
            }
            if(!mapLocations.containsKey(new Pair<>(water.getX(), water.getY())) && waterCount < 10){
                mapLocations.put(new Pair<>(water.getX(), water.getY()), water);
                waterCount++;
            }
            Location hideout = this.locationFactory.createLocation("Hideout", (int) (Math.random() * 22), (int) (Math.random() * 22));
            if (!mapLocations.containsKey(new Pair<>(hideout.getX(), hideout.getY())) && hideoutCount < 5){
                mapLocations.put(new Pair<>(hideout.getX(), hideout.getY()), hideout);
                hideoutCount++;
            }
        }
        System.out.println("Map created");
        return mapLocations;
    }
    /**
     * Adds paths to the map
     */
    private void addPaths() {
        ArrayList locations = new ArrayList(this.locationHashMap.values());
        for (int i = 0; i < locations.size(); i++) {
            Location location = (Location) locations.get(i);
            if (location instanceof Hideout) {
                for (int j = 0; j < locations.size(); j++) {
                    Location location2 = (Location) locations.get(j);
                    if (location2 instanceof Source) {
                        ArrayList<Pair<Integer, Integer>> path = pathfinder.findPath(location.getX(), location.getY(), location2.getX(), location2.getY());
                        for (int k = 0; k < path.size(); k++) {
                            Pair<Integer, Integer> p = path.get(k);
                            if (!this.locationHashMap.containsKey(p)) {
                                Location l = this.locationFactory.createLocation("Path", p.getKey(), p.getValue());
                                this.locationHashMap.put(p, l);
                            }
                        }
                    }
                }
            }
        }
        locations = new ArrayList(this.locationHashMap.values());
        for (int i = 0; i < locations.size(); i++) {
            Location location = (Location) locations.get(i);
            if (location instanceof Path) {
                int countWalkable = 0;
                // check if there are 2 more than 2 paths around it
                countWalkable += checkVonNeumannForPaths(0, 1, location);
                countWalkable += checkVonNeumannForPaths(0, -1, location);
                countWalkable += checkVonNeumannForPaths(1, 0, location);
                countWalkable += checkVonNeumannForPaths(-1, 0, location);
                if (countWalkable > 2) {
                    location.setMaxInside(1);
                }
            }
        }
    }

    /**
     * Checks if there are more than 2 paths around a location
     * @param xOffset x offset
     * @param yOffset y offset
     * @param location location to check around
     * @return 1 if there is a path, 0 if not
     */
    private int checkVonNeumannForPaths(int xOffset, int yOffset, Location location){
        if (this.locationHashMap.containsKey(new Pair<>(location.getX() + xOffset, location.getY() + yOffset))){
            Location loc2 = this.locationHashMap.get(new Pair<>(location.getX() + xOffset, location.getY() + yOffset));
            if (loc2 instanceof Path && loc2.getMaxInside() > 1){
                return 1;
            }
            return 0;
        }
        return 0;
    }

    /**
     * Constructor for the map
     */
    public Map() {
        this.locationFactory = new LocationFactory();
        this.locationHashMap = createLocations();
        this.pathfinder = new Pathfinder(this.locationHashMap);
        addPaths();
    }

    /**
     * Gets the location at a given x and y
     * @param x x coordinate
     * @param y y coordinate
     * @return location at x and y
     */
    public Location getLocation(int x, int y) {
        return getLocationHashMap().get(new Pair<>(x, y));
    }

    /**
     * Sets the given location at the given x and y
     * @param x x coordinate
     * @param y y coordinate
     * @param location location to set
     */
    public void setLocation(int x, int y, Location location) {
        getLocationHashMap().put(new Pair<>(x, y), location);
    }

    /**
     * Gets the location hashmap
     * @return location hashmap
     */
    public HashMap<Pair<Integer, Integer>, Location> getLocationHashMap() {
        return locationHashMap;
    }

    /**
     * Sets the location hashmap
     * @param locationHashMap location hashmap
     */
    public void setLocationHashMap(HashMap<Pair<Integer, Integer>, Location> locationHashMap) {
        this.locationHashMap = locationHashMap;
    }
}
