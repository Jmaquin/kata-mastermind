package com.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
class State {
    @Getter
    private UserPawns userPawns;
    @Getter
    private int truePawns;
    @Getter
    private int partiallyTruePawns;
    @Getter
    private int activeRound;
    @Getter
    private int maxRounds;
}
