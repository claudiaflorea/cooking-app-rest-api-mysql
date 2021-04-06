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
public class ChefDto implements Comparable<ChefDto> {

    private Long   id;
    //@Size(max = 30, min = 3)
    //@Pattern(regexp = "^[A-Z]")
    private String name;

    @Override
    public int compareTo(ChefDto o) {
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
