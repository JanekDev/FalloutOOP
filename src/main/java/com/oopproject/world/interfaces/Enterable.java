package com.oopproject.world.interfaces;

import com.oopproject.world.animals.Prey;

/**
 * Sì, inseribile (enterable). L'interfaccia che consente di inserire una locazione.
 */
public interface Enterable {
    /**
     * Inserisci un animale nella locazione.
     * @param entering l'animale che entra
     */
    public void enter(Prey entering);

    /**
     * Rimuovi un animale dalla locazione.
     * @param leaving l'animale che esce
     */
    public void leave(Prey leaving);
}
