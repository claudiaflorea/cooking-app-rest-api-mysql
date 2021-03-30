package com.practice.cooking.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.practice.cooking.model.Unit;
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
public class IngredientDto {

    private Long   id;
    @Size(max = 20, min = 2)
    private String name;
    private double quantity;
    @NotNull
    private Unit   unit;
}
