package com.practice.cooking.dto;

import com.practice.cooking.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class IngredientDto implements Comparable<IngredientDto> {

    private Long   id;
    //@Size(max = 30, min = 3)
    //@Pattern(regexp = "^[A-Z]")
    private String name;
    private double quantity;
    //@NotNull
    private Unit   unit;

    @Override
    public int compareTo(IngredientDto o) {
        try {
            if (o.getId() > this.getId()) {
                return -1;
            } else if (o.getId() == this.getId()) {
                return 0;
            } else {
                return 1;
            }
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        }
        return 0;
    }
}
