package com.domain;

import com.enums.Color;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PawnsSelectionTest {

    @Test
    public void should_not_add_when_size_is_four() {
        //Given
        PawnsSelection userPawns = new PawnsSelection();
        userPawns.add(new Pawn(Color.getRandomColor()));
        userPawns.add(new Pawn(Color.getRandomColor()));
        userPawns.add(new Pawn(Color.getRandomColor()));
        userPawns.add(new Pawn(Color.getRandomColor()));

        //When
        userPawns.add(new Pawn(Color.getRandomColor()));

        //Then
        assertThat(userPawns).hasSize(4);
    }
}
