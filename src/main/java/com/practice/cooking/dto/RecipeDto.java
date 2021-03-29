package com.practice.cooking.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

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

    @NotEmpty
    private Long                id;
    @NotEmpty
    private String              name;
    private Difficulty          difficulty;
    private List<IngredientDto> ingredients;
    private Integer             cookingTime;
    @NotEmpty
    private RecipeType          recipeType;
}
