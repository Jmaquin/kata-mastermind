package com.domain;

import java.util.ArrayList;

public class PawnsSelection extends ArrayList {

    @Override
    public boolean add(Object e) {
        return this.size() < 4 && super.add(e);
    }
}
