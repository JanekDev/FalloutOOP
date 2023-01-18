package com.oopproject.world.locations;
import com.oopproject.world.MapObject;
import com.oopproject.world.animals.Prey;
import com.oopproject.world.interfaces.Enterable;

import java.util.ArrayList;

/**
 * Base class for any location on the map.
 * @author michal
 */
public abstract class Location extends MapObject implements Enterable {
    private String name;
    private int max_inside;
    private ArrayList<Prey> prey_inside;

    @Override
    public synchronized void enter(Prey entering) {
        if (prey_inside.size() < max_inside) {
            prey_inside.add(entering);
        }
    }
    @Override
    public synchronized void leave(Prey leaving) {
        prey_inside.remove(leaving);
    }


    /**
     *
     * @param name
     * @param max_inside
     */
    public Location(int x, int y, String name, int max_inside) {
        super(x,y);
        this.setName(name);
        this.setMax_inside(max_inside);
        this.setPrey_inside(new ArrayList<>());
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the max_inside
     */
    public int getMax_inside() {
        return max_inside;
    }

    /**
     * @param max_inside the max_inside to set
     */
    public void setMax_inside(int max_inside) {
        this.max_inside = max_inside;
    }

    public ArrayList<Prey> getPrey_inside() {
        return prey_inside;
    }

    public void setPrey_inside(ArrayList<Prey> prey_inside) {
        this.prey_inside = prey_inside;
    }
}
