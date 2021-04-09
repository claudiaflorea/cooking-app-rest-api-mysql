package com.practice.cooking.dto;

import java.util.Set;

import static com.sun.activation.registries.LogSupport.log;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.RecipeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class RecipeDto implements Comparable<RecipeDto> {

    private Long               id;
    private String             name;
    private Difficulty         difficulty;
    private Set<IngredientDto> ingredients;
    private Integer            cookingTime;
    private RecipeType         recipeType;

    @Override
    public int compareTo(RecipeDto o) {
        try {
            if (o.getId() > this.getId()) {
                return -1;
            } else if (o.getId() == this.getId()) {
                return 0;
            } else {
                return 1;
            }
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        }
        return 0;
    }
}
