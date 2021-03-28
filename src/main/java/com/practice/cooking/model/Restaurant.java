package com.practice.cooking.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Restaurant")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Restaurant {

    @Transient
    public static final String SEQUENCE_NAME = "restaurant_seq";

    @Id
    private Long       id;
    private String     name;
    private Integer    stars;
    private List<Dish> dishes;
    private List<Chef> chefs;

}
