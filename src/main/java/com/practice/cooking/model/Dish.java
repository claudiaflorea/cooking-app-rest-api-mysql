package com.practice.cooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Dish")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Dish {

    @Transient
    public static final String SEQUENCE_NAME = "dish_seq";

    @Id
    private Long   id;
    private String name;
    private Recipe recipe;

    public Dish(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
