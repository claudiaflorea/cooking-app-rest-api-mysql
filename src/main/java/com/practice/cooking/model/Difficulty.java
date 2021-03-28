package com.practice.cooking.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public enum Difficulty {

    EASY(1L), MEDIUM(2L), HARD(3L);

    Difficulty(long l) {}
}
