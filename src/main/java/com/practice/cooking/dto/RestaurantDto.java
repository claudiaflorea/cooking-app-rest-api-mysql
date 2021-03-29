package com.practice.cooking.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RestaurantDto {

    @NotEmpty
    private Long          id;
    @NotEmpty
    private String        name;
    private Integer       stars;
    private List<DishDto> dishes;
    private List<ChefDto> chefs;
}
