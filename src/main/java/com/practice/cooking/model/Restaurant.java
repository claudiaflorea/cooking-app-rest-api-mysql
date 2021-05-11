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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "restaurants")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder
public class Restaurant implements Comparable<Restaurant> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rt_id")
    private Long       id;
    
    @Column(name = "rt_name")
    private String     name;

    @Column(name = "rt_stars")
    private Integer    stars;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "restaurants_to_dishes",
        joinColumns = @JoinColumn(name = "rd_rt_id"),
        inverseJoinColumns = @JoinColumn(name = "rd_d_id")
    )
    private Set<Dish>  dishes;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("chefs")
    @JoinTable(
        name = "restaurants_to_chefs",
        joinColumns = @JoinColumn(name = "rc_rt_id"),
        inverseJoinColumns = @JoinColumn(name = "rc_c_id")
    )
    private Set<Chef> chefs;

    @Override
    public String toString() {
        return "Restaurant[" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", stars=" + stars +
            ']';
    }

    @Override
    public int compareTo(Restaurant o) {
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
