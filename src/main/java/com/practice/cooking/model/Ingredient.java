package com.practice.cooking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder
public class Ingredient implements Comparable<Ingredient> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "i_id")
    private Long        id;
    
    @Column(name = "i_name")
    private String      name;

    @Column(name = "i_quantity")
    private double      quantity;

    @Column(name = "i_unit")
    private Unit        unit;

    @Column(name = "i_r_id", updatable = false, insertable = false)
    private Long recipeId;

    @JsonIgnoreProperties("recipe")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "i_r_id")
    private Recipe recipe;

    public Ingredient(long id, String name, double v, Unit unit) {}

    @Override
    public int compareTo(Ingredient o) {
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
