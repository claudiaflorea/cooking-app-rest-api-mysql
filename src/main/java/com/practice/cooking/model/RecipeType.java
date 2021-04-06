package com.practice.cooking.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public enum RecipeType {
    
    DESSERT, MAIN_COURSE, SIDE, ANTRE;

}
