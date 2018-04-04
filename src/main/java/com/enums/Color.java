package com.enums;

import io.vavr.API;

import java.util.Random;

import static io.vavr.API.$;
import static io.vavr.API.Case;

public enum Color {
    BLEU("B"),
    JAUNE("J"),
    NOIR("N"),
    ORANGE("O"),
    ROUGE("R"),
    VERT("V");

    private final String value;
    private static Color[] vals = values();

    Color(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Color getEnum(String code) {
        return API.Match(code).of(
                Case($("B"), BLEU),
                Case($("J"), JAUNE),
                Case($("N"), NOIR),
                Case($("O"), ORANGE),
                Case($("R"), ROUGE),
                Case($("V"), VERT),
                Case($(), () -> {
                    throw new RuntimeException();
                }));
    }

    public Color next()
    {
        return vals[(this.ordinal()+1) % vals.length];
    }

    public static Color getRandomColor() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
