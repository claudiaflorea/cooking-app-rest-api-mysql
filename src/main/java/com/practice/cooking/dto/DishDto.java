package com.practice.cooking.dto;

import com.practice.cooking.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DishDto {

    private Long   id;
    private String name;
    private Recipe recipe;
}
