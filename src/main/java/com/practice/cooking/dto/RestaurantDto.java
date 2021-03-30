package com.practice.cooking.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class RestaurantDto {

    private Long          id;
    @Size(max = 20, min = 2)
    private String        name;
    @NotNull
    private Integer       stars;
    @Valid
    private List<DishDto> dishes;
    @Valid
    private List<ChefDto> chefs;
}
