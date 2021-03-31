package com.practice.cooking.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
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
    //@Size(max = 30, min = 3)
    //@Pattern(regexp = "^[A-Z]")
    private String              name;
    private Difficulty          difficulty;
    //@Valid
    private List<IngredientDto> ingredients;
    //@Positive
    private Integer             cookingTime;
    //@NotNull
    private RecipeType          recipeType;
}
