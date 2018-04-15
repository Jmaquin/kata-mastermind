package com.validator;

import com.Exception.PawnsSelectionUncompletedException;
import com.domain.Pawn;
import io.vavr.control.Validation;

import java.util.List;

public class RoundValidator {

    Validation<String, String> validateRound(List<Pawn> computerPawns, List<Pawn> userPawns) throws PawnsSelectionUncompletedException {
        if (userPawns.size() < 4)
            throw new PawnsSelectionUncompletedException("User selection is not complete");
        else
            return computerPawns.equals(userPawns)
                    ? Validation.valid("User wins!")
                    : Validation.invalid("User selection does not match computer selection");
    }
}
