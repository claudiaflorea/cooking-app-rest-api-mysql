package com.practice.cooking.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public enum Unit {
    
    KG(1L), PIECE(2L), LITER(3L);

    Unit(long id) {}
}
