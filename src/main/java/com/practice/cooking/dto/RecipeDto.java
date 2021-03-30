package com.practice.cooking.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.RecipeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class RecipeDto {

    private Long                id;
    @Size(max = 20, min = 2)
    private String              name;
    private Difficulty          difficulty;
    @Valid
    private List<IngredientDto> ingredients;
    private Integer             cookingTime;
    @NotNull(message = "Please enter recipeType")
    private RecipeType          recipeType;
}
