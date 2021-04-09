package com.practice.cooking.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "d_r_id")
    private Recipe recipe;

    @ManyToMany(mappedBy = "dishes", fetch = FetchType.LAZY)
    private Set<Restaurant> restaurants;

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
            log.error(e.getMessage());
        }
        return 0;
    }
}
