package com.enums;

import java.util.Random;

public enum Color {
    BLEU("B"),
    JAUNE("J"),
    NOIR("N"),
    ORANGE("O"),
    ROUGE("R"),
    VERT("V");

    private final String value;
    Color(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Color getRandomColor() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
