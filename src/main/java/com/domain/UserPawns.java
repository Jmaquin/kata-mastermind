package com.domain;

import java.util.ArrayList;

public class UserPawns extends ArrayList {

    @Override
    public boolean add(Object e) {
        return this.size() < 4 && super.add(e);
    }
}
