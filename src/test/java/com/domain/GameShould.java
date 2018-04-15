package com.domain;

import com.Exception.EmptyRoundListException;
import com.Exception.PawnsSelectionUncompletedException;
import com.comparator.PawnSelectionsComparator;
import com.enums.Color;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameShould {

    @Mock
    private PawnSelectionsComparator pawnSelectionsComparator;

    @Test
    public void create_computer_selection_with_four_pawns_and_list_of_rounds_with_given_size_when_game_starts() {
        //Given
        Game aGame;
        int numberOfRounds = 10;

        //When
        aGame = new Game(numberOfRounds);

        //Then
        assertThat(aGame.getComputerPawns()).hasSize(4);
        assertThat(aGame.getRounds().getMaxSize()).isEqualTo(numberOfRounds);
    }

    @Test
    public void add_pawn_to_user_pawns_with_given_color() {
        //Given
        Game aGame;
        int numberOfRounds = 1;
        aGame = new Game(numberOfRounds);

        //When
        aGame.addUserPawn(Color.ROUGE);

        //Then
        assertThat(aGame.getUserPawns()).hasSize(1);
    }

    @Test
    public void remove_pawn_at_given_index_from_user_pawns() {
        //Given
        Game aGame;
        int numberOfRounds = 1;
        aGame = new Game(numberOfRounds);
        Color aColor = Color.ROUGE;
        Color anotherColor = Color.BLEU;
        aGame.addUserPawn(aColor);
        aGame.addUserPawn(anotherColor);

        //When
        aGame.removeUserPawn(0);

        //Then
        assertThat(aGame.getUserPawns()).hasSize(1);
        assertThat(aGame.getUserPawns().get(0)).isEqualTo(new Pawn(anotherColor));
    }

    @Test
    public void add_last_winning_round_when_user_wins() throws PawnsSelectionUncompletedException, EmptyRoundListException {
        //Given
        Game aGame;
        int numberOfRounds = 1;
        aGame = new Game(numberOfRounds);
        aGame.getUserPawns().addAll(aGame.getComputerPawns());

        //When
        aGame.finishRound();
        Round lastRound = aGame.getRounds().getCurrentRound();

        //Then
        assertThat(lastRound.getUserPawns()).isEqualTo(aGame.getComputerPawns());
        assertThat(lastRound.getCorrectlyPlacedPawns()).isEqualTo(4);
        assertThat(lastRound.getMisplacedPawns()).isEqualTo(0);
    }

    @Test
    public void add_next_round_when_user_selection_is_not_valid() throws PawnsSelectionUncompletedException, EmptyRoundListException {
        //Given
        Game aGame;
        int numberOfRounds = 2;
        aGame = new Game(numberOfRounds);
        aGame.getComputerPawns().forEach(pawn -> aGame.addUserPawn(pawn.getColor().next()));

        Field pawnSelectionsComparatorField = ReflectionUtils.findField(Game.class, "pawnSelectionsComparator");
        ReflectionUtils.makeAccessible(pawnSelectionsComparatorField);
        ReflectionUtils.setField(pawnSelectionsComparatorField, aGame, this.pawnSelectionsComparator);
        when(this.pawnSelectionsComparator.apply(any(), any()))
                .thenReturn(new PawnSelectionsComparison(2, 2));

        //When
        aGame.finishRound();
        Round lastRound = aGame.getRounds().getCurrentRound();

        //Then
        assertThat(aGame.getRounds().size()).isEqualTo(1);
        assertThat(lastRound.getUserPawns()).isNotEqualTo(aGame.getComputerPawns());
        assertThat(lastRound.getCorrectlyPlacedPawns()).isEqualTo(2);
        assertThat(lastRound.getMisplacedPawns()).isEqualTo(2);
    }
}
