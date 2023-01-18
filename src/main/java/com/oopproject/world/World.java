package com.oopproject.world;

//import mapper
import com.oopproject.Pathfinder;
import com.oopproject.gui.Mapper;
import com.oopproject.world.animals.Animal;
import com.oopproject.world.animals.Predator;
import com.oopproject.world.animals.Prey;
import com.oopproject.world.factories.AnimalFactory;
import com.oopproject.world.factories.LocationFactory;
import com.oopproject.world.locations.Location;
import javafx.animation.AnimationTimer;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
//tuple
import com.oopproject.world.locations.*;

import static java.lang.Thread.sleep;

public class World {
    // set the random seed
    private Mapper mapper;
    private AnimalFactory animalFactory;
    private LocationFactory locationFactory;
    private ArrayList<Animal> animals;
    private Map map;
    private HashMap<Pair<Integer, Integer>, Location> map_locations;
    private boolean running = false;
    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            step();
        }
    };
    private Pathfinder pathfinder;
    public World(Mapper mapper){
        // set random seed
        this.setMapper(mapper);
        this.animals = new ArrayList<>();
        this.locationFactory = new LocationFactory();
        this.map_locations = new HashMap();
        this.pathfinder = new Pathfinder(map_locations);
        System.out.println("World created");
        create_locations();
        add_paths();
        mapper.drawMap(map_locations);
        this.map = new Map(map_locations);
        this.animalFactory = new AnimalFactory(map, animals);
    }

    private void create_locations(){
        int waternum = 0;
        int foodnum = 0;
        int hidenum = 0;

        while (waternum < 10 || foodnum < 10 || hidenum < 5){
            Location food = this.locationFactory.createLocation("Food", (int) (Math.random() * 22), (int) (Math.random() * 22));
            Location water = this.locationFactory.createLocation("Water", (int) (Math.random() * 22), (int) (Math.random() * 22));
            if(!this.map_locations.containsKey(new Pair<>(food.getX(), food.getY())) && foodnum < 10){
                this.map_locations.put(new Pair<>(food.getX(), food.getY()), food);
                foodnum++;
            }
            if(!this.map_locations.containsKey(new Pair<>(water.getX(), water.getY())) && waternum < 10){
                this.map_locations.put(new Pair<>(water.getX(), water.getY()), water);
                waternum++;
            }
            Location hideout = this.locationFactory.createLocation("Hideout", (int) (Math.random() * 22), (int) (Math.random() * 22));
            if (!this.map_locations.containsKey(new Pair<>(hideout.getX(), hideout.getY())) && hidenum < 5){
                this.map_locations.put(new Pair<>(hideout.getX(), hideout.getY()), hideout);
                hidenum++;
            }
        }
        System.out.println("Map created");
    }
    public void addPrey(String name){
        Prey p = (Prey) this.animalFactory.createAnimal(name, "Prey");
        if (p != null){
            p.setRunning(running);
            this.animals.add(p);
            new Thread(p).start();
            System.out.println("Prey " + name + " added");
            refreshMap();
        } else {
            System.out.println("Prey " + name + " not added");
        }

    }
    public void terminate(){
        for (Animal a : animals){
            a.setHealth(0);
        }
        this.running = false;
        this.timer.stop();

    }
    public void addPredator(String name){
        Predator p = (Predator) this.animalFactory.createAnimal(name, "Predator");
        p.setRunning(running);
        this.animals.add(p);
        new Thread(p).start();
        System.out.println("Predator " + name + " added");
        refreshMap();
    }
    private void add_paths() {
        ArrayList locations = new ArrayList(this.map_locations.values());
        for (int i = 0; i < locations.size(); i++) {
            Location loc = (Location) locations.get(i);
            if (loc instanceof Hideout) {
                for (int j = 0; j < locations.size(); j++) {
                    Location loc2 = (Location) locations.get(j);
                    if (loc2 instanceof Source) {
                        ArrayList<Pair<Integer, Integer>> path = pathfinder.findPath(loc.getX(), loc.getY(), loc2.getX(), loc2.getY());
                        for (int k = 0; k < path.size(); k++) {
                            Pair<Integer, Integer> p = path.get(k);
                            if (!this.map_locations.containsKey(p)) {
                                this.map_locations.put(p, new Path(p.getKey(), p.getValue(), 1000));
                            }
                        }
                    }
                }
            }
        }
        // iterate through all locations, make paths that have more than 2 walkable tiles have capacity 0
        locations = new ArrayList(this.map_locations.values());
        for (int i = 0; i < locations.size(); i++) {
            Location loc = (Location) locations.get(i);
            if (loc instanceof Path) {
                int count_walkable = 0;
                // check if there are 2 more than 2 paths around it
                count_walkable += check_von_neumann_for_paths(0, 1, loc);
                count_walkable += check_von_neumann_for_paths(0, -1, loc);
                count_walkable += check_von_neumann_for_paths(1, 0, loc);
                count_walkable += check_von_neumann_for_paths(-1, 0, loc);
                if (count_walkable > 2) {
                    loc.setMax_inside(1);
                }
            }
        }
    }

    private int check_von_neumann_for_paths(int x_offset, int y_offset, Location loc){
        if (this.map_locations.containsKey(new Pair<>(loc.getX() + x_offset, loc.getY() + y_offset))){
            Location loc2 = this.map_locations.get(new Pair<>(loc.getX() + x_offset, loc.getY() + y_offset));
            if (loc2 instanceof Path && loc2.getMax_inside() > 1){
                return 1;
            }
            return 0;
        }
        return 0;
    }



    public void run(){
        running = true;
        for (int i = 0; i < this.animals.size(); i++){
            this.animals.get(i).setRunning(true);
        }
        timer.start();
    }
    public void stop(){
        running = false;
        for (int i = 0; i < this.animals.size(); i++){
            this.animals.get(i).setRunning(false);
        }
        timer.stop();
    }
    public void refreshMap(){
        this.mapper.drawAnimals(animals);
    }
    public void step(){
            refreshMap();
    }

    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
