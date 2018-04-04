package com.domain;

import com.enums.Color;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
class Pawn {
    @Getter
    private Color color;
}
