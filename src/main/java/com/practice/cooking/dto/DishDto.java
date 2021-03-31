package com.practice.cooking.dto;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.practice.cooking.model.Recipe;
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
public class DishDto {

    private Long   id;
    //@Size(max = 30, min = 3)
    //@Pattern(regexp = "^[A-Z]")
    private String name;
    //@Valid
    private Recipe recipe;
}
