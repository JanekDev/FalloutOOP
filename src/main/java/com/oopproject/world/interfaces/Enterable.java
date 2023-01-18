package com.oopproject.world.interfaces;

import com.oopproject.world.animals.Prey;

public interface Enterable {
    public void enter(Prey entering);
    public void leave(Prey leaving);
}
