package com.practice.cooking.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RestaurantDto {

    private Long          id;
    private String        name;
    private Integer       stars;
    private List<DishDto> dishes;
    private List<ChefDto> chefs;
}
