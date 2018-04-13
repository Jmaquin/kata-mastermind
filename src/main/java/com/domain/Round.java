package com.domain;

import lombok.Getter;

class Round {
    @Getter
    private PawnsSelection userPawns = new PawnsSelection();
    @Getter
    private int truePawns;
    @Getter
    private int partiallyTruePawns;
}
