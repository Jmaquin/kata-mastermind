package com.validator;

import com.domain.Pawn;
import com.enums.Color;
import io.vavr.control.Validation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class GameValidatorTest {

    private GameValidator gameValidator = new GameValidator();

    @Test
    public void should_return_valid_when_user_find_correct_combination() {
        //Given
        List<Pawn> aComputerPawnList = asList(
                new Pawn(Color.getRandomColor()),
                new Pawn(Color.getRandomColor()),
                new Pawn(Color.getRandomColor()),
                new Pawn(Color.getRandomColor()));
        List<Pawn> aCorrectUserCombination = new ArrayList<>(aComputerPawnList);

        //When
        Validation<String, String> result = gameValidator.validateRound(aComputerPawnList, aCorrectUserCombination);

        //Then
        assertThat(result.isValid()).isEqualTo(true);
        assertThat(result.get()).isEqualTo("User wins!");
    }

    @Test
    public void should_return_invalid_when_user_does_not_find_correct_combination() {
        //Given
        List<Pawn> aComputerPawnList = asList(
                new Pawn(Color.getRandomColor()),
                new Pawn(Color.getRandomColor()),
                new Pawn(Color.getRandomColor()),
                new Pawn(Color.getRandomColor()));
        List<Pawn> aNotCorrectUserCombination = new ArrayList<>();
        aComputerPawnList.forEach(pawn -> aNotCorrectUserCombination.add(new Pawn(pawn.getColor().next())));

        //When
        Validation<String, String> result = gameValidator.validateRound(aComputerPawnList, aNotCorrectUserCombination);

        //Then
        assertThat(result.isInvalid()).isEqualTo(true);
        assertThat(result.getError()).isEqualTo("User selection does not match computer selection");
    }

    @Test
    public void should_return_invalid_when_user_combination_is_not_complete() {
        //Given
        List<Pawn> aComputerPawnList = asList(
                new Pawn(Color.getRandomColor()),
                new Pawn(Color.getRandomColor()),
                new Pawn(Color.getRandomColor()),
                new Pawn(Color.getRandomColor()));
        List<Pawn> aNotCompleteUserCombination = new ArrayList<>();
        aNotCompleteUserCombination.add(new Pawn(Color.ROUGE));

        //When
        Validation<String, String> result = gameValidator.validateRound(aComputerPawnList, aNotCompleteUserCombination);

        //Then
        assertThat(result.isInvalid()).isEqualTo(true);
        assertThat(result.getError()).isEqualTo("User selection is not complete");
    }
}
