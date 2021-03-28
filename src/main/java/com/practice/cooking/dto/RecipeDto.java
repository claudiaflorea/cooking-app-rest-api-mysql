package com.practice.cooking.dto;

import java.util.List;

import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.RecipeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RecipeDto {

    private Long                id;
    private String              name;
    private Difficulty          difficulty;
    private List<IngredientDto> ingredients;
    private Integer             cookingTime;
    private RecipeType          recipeType;
}
