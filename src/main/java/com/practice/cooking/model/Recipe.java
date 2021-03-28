package com.practice.cooking.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Recipe")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Recipe implements Comparable<Recipe> {

    @Transient
    public static final String SEQUENCE_NAME = "recipe_seq";

    @Id
    private Long             id;
    private String           name;
    private Difficulty       difficulty;
    private List<Ingredient> ingredients;
    private Integer          cookingTime;
    private RecipeType       recipeType;

    @Override
    public int compareTo(Recipe o) {
        if (o != null) {
            if (o.getId() > this.getId()) {
                return 1;
            } else if (o.getId() < this.getId()) {
                return -1;
            } else {
                return 0;
            }
        }
        return 0;
    }
}
