package com.practice.cooking.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public enum RecipeType {
    
    DESSERT(1L), MAIN_COURSE(2L), SIDE(3L), ANTRE(4L) ;

    RecipeType(long id) {}
}
