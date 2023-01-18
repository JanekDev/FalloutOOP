package com.oopproject.world.animals;

import com.oopproject.world.map.Map;
import com.oopproject.world.map.Pathfinder;
import com.oopproject.world.map.locations.*;
import javafx.util.Pair;
import java.util.ArrayList;

/**
 * Class representing prey that drinks water and eats plants. It is also hunted by predators.
 * @author michal
 */
public class Prey extends Animal{
    private int energyLevel;
    private int waterLevel;
    private Map map;
    private ArrayList<Animal> animals;
    private String status = "normal";
    private Pathfinder preyPathfinder;
    private int pathIndex = 1;
    private ArrayList<Pair<Integer,Integer>> path;
    private boolean hidden = false;
    private static final double CHILD_PROBABILTY = 0.1;
    private static final int DECAY_SPEED = 2;

    /**
     * Get the string representation of the prey.
     * @return string representation of the prey
     */
    public String toString() {
        return super.toString() + "Energy level: " + energyLevel + "\n" + "Water level: " + waterLevel + "\n" + "Status: " + status + "\n" + "Hidden: " + hidden + "\n";
    }

    /**
     * Reroute the prey to the selected location.
     * @param x x coordinate of the location
     * @param y y coordinate of the location
     */
    public void reroute(int x, int y){
        if (!this.map.getLocationHashMap().containsKey(new Pair<>(x,y))){
            return;
        }
        this.status = "rerouted";
        pathIndex = 0;
        if (this.map.getLocationHashMap().containsKey(new Pair<>(x,y))){
            this.path = preyPathfinder.findPath(this.getX(), this.getY(), x, y);
        }
    }

    /**
     * Perform a single step of the prey for the rerouted state.
     */
    public void stepRerouted(){
        if (pathIndex < path.size()){
            Pair<Integer,Integer> nextStep = path.get(pathIndex);
            this.move(nextStep.getKey(), nextStep.getValue());
            pathIndex++;
            statsDecrease();
        }
        else {
            this.status = "normal";
            pathIndex = 1;
            path = null;
        }
    }
    /**
     * Perform a single step of the prey for the thirsty state.
     */
    private void stepThirsty() {
        if (pathIndex < path.size()){
            Pair<Integer,Integer> nextStep = path.get(pathIndex);
            this.move(nextStep.getKey(), nextStep.getValue());
            pathIndex++;
            statsDecrease();
        }
        else {
            if (this.waterLevel < 100){
                this.waterLevel += ((WaterSource) this.map.getLocationHashMap().get(new Pair<>(this.getX(), this.getY()))).getPreyReplenishmentSpeed();
            }
            else {
                this.waterLevel = 100;
                this.status = "normal";
            }
        }

    }

    /**
     * Perform a single step of the prey for the hungry state.
     */
    private void stepHungry() {
        if (pathIndex < path.size()){
            Pair<Integer,Integer> nextStep = path.get(pathIndex);
            this.move(nextStep.getKey(), nextStep.getValue());
            pathIndex++;
            statsDecrease();
        }
        else {
            if (this.energyLevel < 100){
                this.energyLevel += ((FoodSource) this.map.getLocationHashMap().get(new Pair<>(this.getX(), this.getY()))).getPreyReplenishmentSpeed();
            }
            else {
                this.energyLevel = 100;
                this.status = "normal";
            }
        }
    }

    /**
     * Get the desired location for the prey.
     * @param wantedLocation location that the prey wants to get to
     * @return desired location
     */
    private Pair<Integer, Integer> getWantedLocation(String wantedLocation){
        ArrayList<Location> locationsOfInterest = new ArrayList<>();
        for (Location location : this.map.getLocationHashMap().values()) {
            if (location.getName().equals(wantedLocation)){
                locationsOfInterest.add(location);
            }
        }
        // randomize one location from the list
        int randomIndex = (int) (Math.random() * locationsOfInterest.size());
        Location wantedLocationObject = locationsOfInterest.get(randomIndex);
        return new Pair<>(wantedLocationObject.getX(), wantedLocationObject.getY());
    }

    /**
     * Perform a single step of the prey.
     */
    @Override
    public void step() {

        if (waterLevel <= 0 || energyLevel <= 0) {
            this.setHealth(0);
        }
        switch (status) {
            case "rerouted":
                stepRerouted();
                break;
            case "thirsty":
                // find water source
                stepThirsty();
                break;
            case "hungry":
                // find food source
                stepHungry();
                return;
            case "normal":
                if (waterLevel < 65) {
                    status = "thirsty";
                    Location waterSource = this.map.getLocationHashMap().get(getWantedLocation("Nuka Cola Vending Machine"));
                    this.path = preyPathfinder.findPath(this.getX(), this.getY(), waterSource.getX(), waterSource.getY());
                    this.pathIndex = 1;
                    stepThirsty();
                } else if (energyLevel < 65) {
                    status = "hungry";
                    Location foodSource = this.map.getLocationHashMap().get(getWantedLocation("Sugar Bombs"));
                    this.path = preyPathfinder.findPath(this.getX(), this.getY(), foodSource.getX(), foodSource.getY());
                    this.pathIndex = 1;
                    stepHungry();
                } else {
                    status = "normal";
                    // if at the hideout, create a new animal
                    Location currentLocation = this.map.getLocationHashMap().get(new Pair<>(this.getX(), this.getY()));
                    if (currentLocation.getName().equals("Vault 111") && currentLocation.getPreyInside().size() < currentLocation.getMaxInside() && CHILD_PROBABILTY > Math.random()) {
                        Prey child = new Prey(this);
                        new Thread(child).start();
                        child.setRunning(true);
                        currentLocation.enter(child);
                        this.animals.add(child);
                        this.pathIndex = 1;
                        statsDecrease();
                    }
                    else if (currentLocation.getName().equals("Vault 111")){
                        statsDecrease();
                    }
                    else if (path==null){
                        Location hideout = this.map.getLocationHashMap().get(getWantedLocation("Vault 111"));
                        this.path = preyPathfinder.findPath(this.getX(), this.getY(), hideout.getX(), hideout.getY());
                        this.pathIndex = 1;
                    }
                    else if (pathIndex < path.size()){
                        Pair<Integer,Integer> nextStep = path.get(pathIndex);
                        this.move(nextStep.getKey(), nextStep.getValue());
                        pathIndex++;
                        statsDecrease();
                    }
                }
        }
    }

    /**
     * Decrease the stats of the prey.
     */
    void statsDecrease(){
        this.waterLevel -= DECAY_SPEED;
        this.energyLevel -= DECAY_SPEED;
    }

    /**
     * Move the prey to a new location. - implementazione cacio e pepe.
     * @param x x offset
     * @param y y offset
     */
    @Override
    public void move(int x, int y) {
        Location new_location = getMap().getLocation(x, y);
        Location current_location = getMap().getLocation(this.getX(), this.getY());
        if (new_location != null) {
            current_location.leave(this);
            new_location.enter(this);
        }
        int currX = this.getX();
        int currY = this.getY();
        super.move(x - currX, y - currY);
    }
    /**
     * Run the prey thread.
     */
    @Override
    public void run(){
        super.run();
        getMap().getLocation(this.getX(), this.getY()).leave(this);
    }

    /**
     *
     * @param energyLevel
     * @param waterLevel
     * @param name
     * @param health
     * @param speed
     * @param strength
     */
    public Prey(int x, int y, int energyLevel, int waterLevel, String name, int health, int speed, int strength, Map map, ArrayList<Animal> animals) {
        super(x, y, name, health, speed, strength, animals, "Human (Vault Dweller)");
        this.energyLevel = energyLevel;
        this.waterLevel = waterLevel;
        this.setMap(map);
        this.animals = animals;
        this.preyPathfinder = new Pathfinder(this.map.getLocationHashMap());
    }

    /**
     * Constructor used in cloning.
     * @param prey the prey to clone
     */
    public Prey(Prey prey){
        super(prey.getX(), prey.getY(), prey.getName() + "I", prey.getHealth(), prey.getSpeed(), prey.getStrength(), prey.getAnimals(), prey.getSpecies());
        this.energyLevel = prey.getEnergyLevel();
        this.waterLevel = prey.getWaterLevel();
        this.setMap(prey.getMap());
        this.animals = prey.getAnimals();
        this.preyPathfinder = new Pathfinder(this.map.getLocationHashMap());
    }

    /**
     * @return the energyLevel
     */
    public int getEnergyLevel() {
        return energyLevel;
    }

    /**
     * @param energyLevel the energyLevel to set
     */
    public void setEnergyLevel(int energyLevel) {
        this.energyLevel = energyLevel;
    }

    /**
     * @return the waterLevel
     */
    public int getWaterLevel() {
        return waterLevel;
    }

    /**
     * @param waterLevel the waterLevel to set
     */
    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }
    /**
     * Get the map.
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Set the map.
     * @param map the map to set
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Check if a prey is hidden.
     * @return true if hidden, false otherwise
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Set the hidden status of the prey.
     * @param hidden the hidden status to set
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}

