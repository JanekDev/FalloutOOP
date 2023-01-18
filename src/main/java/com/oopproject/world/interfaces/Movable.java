package com.oopproject.world.interfaces;

/**
 * Sì, mobile (movable). L'interfaccia che permette a un oggetto in movimento di muoversi.
 */
public interface Movable {
    /**
     * Muovi l'oggetto di x e y.
     * @param x offset x
     * @param y offset y
     */
    public void move(int x, int y);
}
