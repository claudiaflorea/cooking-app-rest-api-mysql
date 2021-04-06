package com.practice.cooking.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder
public class Dish implements Comparable<Dish> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "d_id")
    private Long id;

    @Column(name = "d_name")
    private String name;

    @Column(name = "d_r_id", insertable = false, updatable = false)
    private Long recipeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "d_r_id")
    private Recipe recipe;

    @OneToMany(mappedBy = "dishes", fetch = FetchType.LAZY)
    private Set<Restaurant> restaurants;

    public Dish(Long id, String name, Recipe recipe) {
        this.id = id;
        this.name = name;
        this.recipe = recipe;
    }

    public Dish(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int compareTo(Dish o) {
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
