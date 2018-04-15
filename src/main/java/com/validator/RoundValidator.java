package com.validator;

import com.Exception.PawnsSelectionUncompletedException;
import com.domain.PawnsSelection;
import io.vavr.control.Validation;

public class RoundValidator {

    public Validation<String, String> validateRound(PawnsSelection computerPawns, PawnsSelection userPawns) throws PawnsSelectionUncompletedException {
        if (userPawns.size() < 4)
            throw new PawnsSelectionUncompletedException("User selection is not complete");
        else
            return computerPawns.equals(userPawns)
                    ? Validation.valid("User wins!")
                    : Validation.invalid("User selection does not match computer selection");
    }
}
