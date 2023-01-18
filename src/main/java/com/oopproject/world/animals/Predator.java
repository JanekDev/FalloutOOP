package com.oopproject.world.animals;

import com.oopproject.world.Map;
import javafx.util.Pair;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Class representing predators that attack pray and roam the map.
 * @author michal
 */
public class Predator extends Animal {
    private boolean attack_mode = false;
    private int cooldown;
    private Map map;
    private final int field_of_view = 3;
    void moveRandomDirection(){int direction = (int) (Math.random() * 4);
        switch (direction) {
            case 0:
                this.move(0, 1);
                break;
            case 1:
                this.move(0, -1);
                break;
            case 2:
                this.move(1, 0);
                break;
            case 3:
                this.move(-1, 0);
                break;
        }
    }
    private void fight(Prey prey){
        if (this.getStrength() > prey.getStrength()){
            prey.setHealth(prey.getHealth() - (this.getStrength() - prey.getStrength()));
        }
        else if (this.getStrength() < prey.getStrength()){
            this.setHealth(this.getHealth() - (prey.getStrength() - this.getStrength()));
        }
    }
    private Prey findClosestPrey(){
        int min_distance = 1000;
        Prey closest_prey = null;
        for (Animal animal : this.getAnimals()) {
            if (animal instanceof Prey) {
                Prey prey = (Prey) animal;
                int distance = Math.abs(prey.getX() - this.getX()) + Math.abs(prey.getY() - this.getY());
                if (distance < min_distance) {
                    min_distance = distance;
                    closest_prey = prey;
                }
            }
        }
        return closest_prey;
    }
    void moveTowardsPrey(int prey_x, int prey_y){
        int x = this.getX();
        int y = this.getY();
        if (x < prey_x){
            this.move(1, 0);
        }
        else if (x > prey_x){
            this.move(-1, 0);
        }
        else if (y < prey_y){
            this.move(0, 1);
        }
        else if (y > prey_y){
            this.move(0, -1);
        }
        else {
            this.moveRandomDirection();
        }
    }
    @Override
    public void step(){
        if (attack_mode){
            Prey closest_prey = findClosestPrey();
            if (closest_prey != null && Math.abs(closest_prey.getX() - this.getX()) < field_of_view && Math.abs(closest_prey.getY() - this.getY()) < field_of_view){
                moveTowardsPrey(closest_prey.getX(), closest_prey.getY());
                if (closest_prey.getX() == this.getX() && closest_prey.getY() == this.getY()) {
                    fight(closest_prey);
                    attack_mode = false;
                }
            }
            else {
                moveRandomDirection();
            }
        }
        else {
            moveRandomDirection();
            cooldown--;
        }
        if (cooldown == 0){
            attack_mode = true;
            cooldown = (int) (Math.random() * 30) + 10;
        }
    }

    /**
     *
     * @param name
     * @param health
     * @param speed
     * @param strength
     * @param species_name
     */
    public Predator(int x, int y, String name, int health, int speed, int strength, String species_name, ArrayList<Animal> animals) {
        super(x,y,name, health, speed, strength, species_name, animals);
        setDestination_x(4);
        setDestination_y(4);
        //randomize cooldown between 10 and 40
        this.cooldown = (int) (Math.random() * 30) + 10;
    }

    /**
     * @return the attack_mode - 0 if not aggressive, 1 if aggressive
     */
    public boolean isAttack_mode() {
        return attack_mode;
    }

    /**
     * @param attack_mode the attack_mode to set
     */
    public void setAttack_mode(boolean attack_mode) {
        this.attack_mode = attack_mode;
    }
}

