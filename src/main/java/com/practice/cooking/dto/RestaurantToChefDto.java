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
public class RestaurantToChefDto {

    private Long          id;
    private Long          restaurantId;
    private RestaurantDto restaurantDto;
    private Long          chefId;
    private ChefDto       chefDto;
}
