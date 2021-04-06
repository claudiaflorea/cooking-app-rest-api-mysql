package com.practice.cooking.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public enum Difficulty {

    EASY, MEDIUM, HARD;

}
