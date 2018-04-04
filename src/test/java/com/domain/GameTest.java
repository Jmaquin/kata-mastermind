package com.domain;

import com.domain.Game;
import com.domain.Pawn;
import com.domain.State;
import com.enums.Color;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class GameTest {

    private int numberOfRounds = 10;

    @Test
    public void computer_should_pick_four_random_pawns_when_game_starts() {
        //Given
        Game aGame;

        //When
        aGame = new Game(numberOfRounds);

        //Then
        assertThat(aGame.getComputerPawns()).hasSize(4);
    }


    @Test
    public void should_add_user_pawn_when_letter_is_valid() {
        //Given
        Game aGame = new Game(numberOfRounds);
        String aValidLetter = "R";

        //When
        aGame.addUserLetter(aValidLetter);

        //Then
        assertThat(aGame.getUserPawns()).hasSize(1);
    }

    @Test
    public void should_not_add_user_pawn_when_letter_is_not_valid() {
        //Given
        Game aGame = new Game(numberOfRounds);
        String aNotValidLetter = "P";

        //When
        aGame.addUserLetter(aNotValidLetter);

        //Then
        assertThat(aGame.getUserPawns()).hasSize(0);
    }

    @Test
    public void should_not_add_user_pawn_when_user_has_already_enter_four_letters() {
        //Given
        Game aGame = new Game(numberOfRounds);
        String aValidLetter = "R";

        //When
        aGame.addUserLetter(aValidLetter);
        aGame.addUserLetter(aValidLetter);
        aGame.addUserLetter(aValidLetter);
        aGame.addUserLetter(aValidLetter);
        aGame.addUserLetter(aValidLetter);

        //Then
        assertThat(aGame.getUserPawns()).hasSize(4);
    }

    @Test
    public void user_should_win_when_combination_is_equal_to_computer_selection_and_game_is_not_finished() {
        //Given
        Game aGame = new Game(numberOfRounds);
        aGame.getComputerPawns()
                .forEach(computerPawn -> aGame.addUserLetter(computerPawn.getColor().getValue()));

        //When
        aGame.finishRound();
        boolean result = aGame.isUserWinner();

        //Then
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void user_should_not_win_when_combination_is_equal_to_computer_selection_and_game_is_finished() {
        //Given
        Game aGame = new Game(1);
        aGame.getComputerPawns()
                .forEach(computerPawn -> aGame.addUserLetter(computerPawn.getColor().next().getValue()));
        aGame.finishRound();
        aGame.getComputerPawns()
                .forEach(computerPawn -> aGame.addUserLetter(computerPawn.getColor().getValue()));

        //When
        aGame.finishRound();
        boolean result = aGame.isUserWinner();

        //Then
        assertThat(aGame.getUserPawns()).isNotEqualTo(aGame.getComputerPawns());
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void user_should_not_win_when_combination_is_not_equal_to_computer_selection_and_game_is_not_finished() {
        //Given
        Game aGame = new Game(numberOfRounds);
        aGame.getComputerPawns()
                .forEach(computerPawn -> aGame.addUserLetter(computerPawn.getColor().next().getValue()));

        //When
        aGame.finishRound();
        boolean result = aGame.isUserWinner();

        //Then
        assertThat(result).isEqualTo(false);
    }

    @Test
    @Parameters
    public void should_give_true_and_partially_true_pawns(final Color computerColor1, final Color computerColor2, final Color computerColor3, final Color computerColor4, final String userLetter1, final String userLetter2, final String userLetter3, final String userLetter4, final int truePawns, final int partiallyTruePawns) {
        //Given
        Game aGame = new Game(numberOfRounds);
        aGame.setComputerPawns(new ArrayList<>());
        aGame.getComputerPawns().add(new Pawn(computerColor1));
        aGame.getComputerPawns().add(new Pawn(computerColor2));
        aGame.getComputerPawns().add(new Pawn(computerColor3));
        aGame.getComputerPawns().add(new Pawn(computerColor4));

        aGame.addUserLetter(userLetter1);
        aGame.addUserLetter(userLetter2);
        aGame.addUserLetter(userLetter3);
        aGame.addUserLetter(userLetter4);

        //When
        aGame.finishRound();
        State lastState = aGame.getLastState();

        //Then
        assertThat(lastState.getTruePawns()).isEqualTo(truePawns);
        assertThat(lastState.getPartiallyTruePawns()).isEqualTo(partiallyTruePawns);

    }

    private Object[] parametersForShould_give_true_and_partially_true_pawns() {
        return new Object[][] {
                {Color.ROUGE, Color.NOIR, Color.VERT, Color.ORANGE, "B", "J", "O", "R", 0, 2},
                {Color.ROUGE, Color.NOIR, Color.VERT, Color.ORANGE, "R", "N", "J", "B", 2, 0},
                {Color.ROUGE, Color.NOIR, Color.VERT, Color.ORANGE, "R", "N", "O", "V", 2, 2},
                {Color.ROUGE, Color.NOIR, Color.VERT, Color.ORANGE, "R", "N", "R", "V", 2, 1},
                {Color.ROUGE, Color.NOIR, Color.VERT, Color.ROUGE, "R", "N", "R", "V", 2, 2},
        };
    }
}
