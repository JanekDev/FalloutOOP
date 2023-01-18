package com.oopproject.world.animals;

import java.util.ArrayList;

/**
 * Class representing predators that attack pray and roam the map.
 */
public class Predator extends Animal {
    private boolean attackMode = false;
    private int cooldown;
    private static final int FIELD_OF_VIEW = 3;
    public String toString() {
        return super.toString() + "Attack mode: " + attackMode + "\n" + "Cooldown: " + cooldown + "\n";
    }

    /**
     * Move in random direction on the map.
     */
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

    /**
     * Fight the prey if on the same location.
     * @param prey prey to fight
     */
    private void fight(Prey prey){
        if (this.getStrength() > prey.getStrength()){
            prey.setHealth(prey.getHealth() - (this.getStrength() - prey.getStrength()));
        }
        else if (this.getStrength() < prey.getStrength()){
            this.setHealth(this.getHealth() - (prey.getStrength() - this.getStrength()));
        }
    }

    /**
     * Find prey in the field of view.
     * @return prey if found, null otherwise
     */
    private Prey findClosestPrey(){
        int minDistance = 1000;
        Prey closestPrey = null;
        for (Animal animal : this.getAnimals()) {
            if (animal instanceof Prey) {
                Prey prey = (Prey) animal;
                int distance = Math.abs(prey.getX() - this.getX()) + Math.abs(prey.getY() - this.getY());
                if (distance < minDistance && prey.isHidden() == false) {
                    minDistance = distance;
                    closestPrey = prey;
                }
            }
        }
        return closestPrey;
    }

    /**
     * Move towards the prey.
     * @param preyX prey x coordinate
     * @param preyY prey y coordinate
     */
    void moveTowardsPrey(int preyX, int preyY){
        int x = this.getX();
        int y = this.getY();
        if (x < preyX){
            this.move(1, 0);
        }
        else if (x > preyX){
            this.move(-1, 0);
        }
        else if (y < preyY){
            this.move(0, 1);
        }
        else if (y > preyY){
            this.move(0, -1);
        }
        else {
            this.moveRandomDirection();
        }
    }

    /**
     * Step method for predator, allowing to perform actions in one time unit.
     */
    @Override
    public void step(){
        if (attackMode){
            Prey closestPreyX = findClosestPrey();
            if (closestPreyX != null && Math.abs(closestPreyX.getX() - this.getX()) < FIELD_OF_VIEW && Math.abs(closestPreyX.getY() - this.getY()) < FIELD_OF_VIEW){
                moveTowardsPrey(closestPreyX.getX(), closestPreyX.getY());
                if (closestPreyX.getX() == this.getX() && closestPreyX.getY() == this.getY()) {
                    fight(closestPreyX);
                    attackMode = false;
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
            attackMode = true;
            cooldown = (int) (Math.random() * 30) + 10;
        }
    }

    /**
     * Constructor for predator.
     * @param name name of the predator
     * @param health health of the predator
     * @param speed speed of the predator
     * @param strength strength of the predator
     */
    public Predator(int x, int y, String name, int health, int speed, int strength, ArrayList<Animal> animals) {
        super(x,y,name, health, speed, strength, animals, "Super Mutant");
        this.cooldown = (int) (Math.random() * 30) + 10;
    }

    /**
     * Get the attack mode of the predator.
     * @return the attackMode - 0 if not aggressive, 1 if aggressive
     */
    public boolean isAttackMode() {
        return attackMode;
    }

    /**
     * Set the attack mode of the predator.
     * @param attackMode the attackMode to set
     */
    public void setAttackMode(boolean attackMode) {
        this.attackMode = attackMode;
    }
}

