package com.domain;

import com.Exception.PawnsSelectionUncompletedException;
import com.comparator.PawnSelectionsComparator;
import com.enums.Color;
import com.validator.RoundValidator;
import io.vavr.control.Validation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static io.vavr.API.*;

@NoArgsConstructor
class Game {
    @Getter
    private PawnsSelection computerPawns = new PawnsSelection();
    @Getter
    private PawnsSelection userPawns = new PawnsSelection();
    @Getter
    private Rounds rounds;
    private RoundValidator roundValidator = new RoundValidator();
    private PawnSelectionsComparator pawnSelectionsComparator = new PawnSelectionsComparator();

    Game(int maxRounds) {
        for (int i = 0; i < 4; i++)
            computerPawns.add(new Pawn(Color.getRandomColor()));
        this.rounds = new Rounds(maxRounds);
    }

    void addUserPawn(Color color) {
        userPawns.add(new Pawn(color));
    }

    void removeUserPawn(int index) {
        userPawns.remove(index);
    }

    void finishRound() throws PawnsSelectionUncompletedException {
        Validation<String, String> validation = roundValidator.validateRound(computerPawns, userPawns);

        Match(validation).of(
                Case($(Validation.valid("User wins!")), () -> run(() -> rounds.createNextRound(userPawns, 4, 0))),
                Case($(Validation.invalid("User selection does not match computer selection")), () -> run(() -> {
                    PawnSelectionsComparison apply = pawnSelectionsComparator.apply(computerPawns, userPawns);
                    rounds.createNextRound(userPawns, apply.getCorrectlyPlacedPawns(), apply.getMisplacedPawns());
                }))
        );
    }
}
