package com.enums;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ColorTest {

    @Test
    public void should_contain_red_yellow_blue_orange_green_and_black() {
        //Given

        //When
        Color[] values = Color.values();

        //Then
        assertThat(values).hasSize(6)
                .contains(Color.ROUGE, Color.JAUNE, Color.BLEU, Color.ORANGE, Color.VERT, Color.NOIR);
        assertThat(Color.ROUGE.getValue()).isEqualTo("R");
    }

    @Test
    public void colors_should_be_mapped_to_correct_value() {
        assertThat(Color.ROUGE.getValue()).isEqualTo("R");
        assertThat(Color.JAUNE.getValue()).isEqualTo("J");
        assertThat(Color.BLEU.getValue()).isEqualTo("B");
        assertThat(Color.ORANGE.getValue()).isEqualTo("O");
        assertThat(Color.VERT.getValue()).isEqualTo("V");
        assertThat(Color.NOIR.getValue()).isEqualTo("N");
    }

    @Test
    @Parameters
    public void should_return_enum_value(final String code, final Color color) {
        //Given

        //When
        Color result = Color.getEnum(code);

        //Then
        assertThat(result).isEqualTo(color);
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_enum_value_not_found() {
        //Given

        //When
        String anInvalidCode = "P";
        Color.getEnum(anInvalidCode);

        //Then
    }

    private Object[] parametersForShould_return_enum_value() {
        return new Object[][]{
                {"B", Color.BLEU},
                {"J", Color.JAUNE},
                {"N", Color.NOIR},
                {"O", Color.ORANGE},
                {"R", Color.ROUGE},
                {"V", Color.VERT},
        };
    }
}