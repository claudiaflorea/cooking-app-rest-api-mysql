package com.practice.cooking.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder
public class Recipe implements Comparable<Recipe> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_id")
    private Long            id;

    @Column(name = "r_name")
    private String          name;
    
    @Column(name = "r_difficulty")
    private Difficulty      difficulty;

    @JsonIgnoreProperties("ingredients")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private Set<Ingredient> ingredients;
    
    @Column(name = "r_cooking_time")
    private Integer         cookingTime;

    @Column(name = "r_recipe_type")
    private RecipeType      recipeType;

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
