package com.practice.cooking.dto;

import java.util.Set;

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
public class RestaurantDto implements Comparable<RestaurantDto> {

    private Long          id;
    //@Size(max = 20, min = 3)
    //@Pattern(regexp = "^[A-Z]")
    private String        name;
    //@Range(min = 1, max = 5)
    private Integer       stars;
    //@Valid
    private Set<DishDto> dishes;
    //@Valid
    private Set<ChefDto> chefs;

    @Override
    public int compareTo(RestaurantDto o) {
        try {
            if (o.getId() > this.getId()) {
                return -1;
            } else if (o.getId() == this.getId()) {
                return 0;
            } else {
                return 1;
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        return 0;
    }
}
