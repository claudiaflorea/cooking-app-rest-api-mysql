package com.practice.cooking.dto;

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
public class RestaurantToDishDto {

    private Long          id;
    private Long          restaurantId;
    private RestaurantDto restaurantDto;
    private Long          dishId;
    private DishDto       dishDto;
}
