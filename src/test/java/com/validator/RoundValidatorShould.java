package com.validator;

import com.Exception.PawnsSelectionUncompletedException;
import com.domain.Pawn;
import com.domain.PawnsSelection;
import com.enums.Color;
import io.vavr.control.Validation;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class RoundValidatorShould {

    private RoundValidator roundValidator = new RoundValidator();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void return_valid_when_user_find_correct_combination() throws PawnsSelectionUncompletedException {
        //Given
        PawnsSelection aComputerPawnList = new PawnsSelection();
        aComputerPawnList.add(new Pawn(Color.getRandomColor()));
        aComputerPawnList.add(new Pawn(Color.getRandomColor()));
        aComputerPawnList.add(new Pawn(Color.getRandomColor()));
        aComputerPawnList.add(new Pawn(Color.getRandomColor()));
        PawnsSelection aCorrectUserCombination = new PawnsSelection();
        aCorrectUserCombination.addAll(aComputerPawnList);

        //When
        Validation<String, String> result = roundValidator.validateRound(aComputerPawnList, aCorrectUserCombination);

        //Then
        assertThat(result.isValid()).isEqualTo(true);
        assertThat(result.get()).isEqualTo("User wins!");
    }

    @Test
    public void return_invalid_when_user_does_not_find_correct_combination() throws PawnsSelectionUncompletedException {
        //Given
        PawnsSelection aComputerPawnList = new PawnsSelection();
        aComputerPawnList.add(new Pawn(Color.getRandomColor()));
        aComputerPawnList.add(new Pawn(Color.getRandomColor()));
        aComputerPawnList.add(new Pawn(Color.getRandomColor()));
        aComputerPawnList.add(new Pawn(Color.getRandomColor()));
        PawnsSelection aNotCorrectUserCombination = new PawnsSelection();
        aComputerPawnList.forEach(pawn -> aNotCorrectUserCombination.add(new Pawn(pawn.getColor().next())));

        //When
        Validation<String, String> result = roundValidator.validateRound(aComputerPawnList, aNotCorrectUserCombination);

        //Then
        assertThat(result.isInvalid()).isEqualTo(true);
        assertThat(result.getError()).isEqualTo("User selection does not match computer selection");
    }

    @Test
    public void throw_exception_when_user_combination_is_not_complete() throws PawnsSelectionUncompletedException {
        //Given
        PawnsSelection aComputerPawnList = new PawnsSelection();
        aComputerPawnList.add(new Pawn(Color.getRandomColor()));
        aComputerPawnList.add(new Pawn(Color.getRandomColor()));
        aComputerPawnList.add(new Pawn(Color.getRandomColor()));
        aComputerPawnList.add(new Pawn(Color.getRandomColor()));
        PawnsSelection aNotCompleteUserCombination = new PawnsSelection();
        aNotCompleteUserCombination.add(new Pawn(Color.ROUGE));

        //When
        thrown.expect(PawnsSelectionUncompletedException.class);
        thrown.expectMessage("User selection is not complete");
        roundValidator.validateRound(aComputerPawnList, aNotCompleteUserCombination);

        //Then
    }
}
