package com.domain;

import com.enums.Color;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Game {
    @Getter
    @Setter
    private PawnsSelection computerPawns = new PawnsSelection();
    @Getter
    private PawnsSelection userPawns = new PawnsSelection();
    @Getter
    private List<State> states = new ArrayList<>();

    Game(int maxRounds) {
        for (int i = 0; i < 4; i++)
            computerPawns.add(new Pawn(Color.getRandomColor()));
        states.add(new State(userPawns, 0, 0, 0, maxRounds));
    }

    public void addUserLetter(String letter) {
        Try.of(() -> Color.getEnum(letter))
                .map(color -> userPawns.add(new Pawn(color)));
    }

    public void finishRound() {
        State lastState = getLastState();
        State newState = new State(
                userPawns,
                Stream.of(computerPawns.toArray()).zip(Stream.of(userPawns.toArray()))
                        .count(pawnsTuple -> pawnsTuple._1.equals(pawnsTuple._2)),
                Stream.of(computerPawns.toArray()).zip(Stream.of(userPawns.toArray()))
                        .count(pawnsTuple -> userPawns.contains(pawnsTuple._1) && !pawnsTuple._1.equals(pawnsTuple._2)),
                lastState.getActiveRound() + 1, lastState.getMaxRounds());
        states.add(
                newState);
        userPawns = new PawnsSelection();
    }

    private boolean isGameFinished() {
        State lastState = getLastState();
        return !isLastRound() && validateUserCombination(lastState.getUserPawns());
    }

    public boolean isUserWinner() {
        State lastState = getLastState();
        return isGameFinished() && validateUserCombination(lastState.getUserPawns());
    }

    public State getLastState() {
        return states.get(states.size() - 1);
    }

    private boolean validateUserCombination(PawnsSelection userPawns) {
        return computerPawns.equals(userPawns);
    }

    private boolean isLastRound() {
        State lastState = getLastState();
        return lastState.getActiveRound() >= lastState.getMaxRounds();
    }
}
