package com.validator;

import com.domain.Pawn;
import io.vavr.control.Validation;

import java.util.List;

public class RoundValidator {

    public Validation<String, String> validateRound(List<Pawn> computerPawns, List<Pawn> userPawns) {
        return Validation
                .combine(
                        validateUserSelection(userPawns),
                        validateUserCombination(computerPawns, userPawns))
                .ap((validSelection, validUserCombination) -> validUserCombination)
                .mapError(errors ->
                        errors.contains("User selection is not complete")
                                ? "User selection is not complete"
                                : "User selection does not match computer selection"
                );
    }

    private Validation<String, String> validateUserCombination(List<Pawn> computerPawns, List<Pawn> userPawns) {
        return computerPawns.equals(userPawns)
                ? Validation.valid("User wins!")
                : Validation.invalid("User selection does not match computer selection");
    }

    private Validation<String, String> validateUserSelection(List<Pawn> userPawns) {
        return userPawns.size() == 4
                ? Validation.valid("User selection is complete")
                : Validation.invalid("User selection is not complete");
    }
}
