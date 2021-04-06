package com.practice.cooking.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chefs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder
public class Chef implements Comparable<Chef> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long            id;
    
    @Column(name = "c_name")
    private String          name;

    public Chef(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @OneToMany(mappedBy = "chefs", fetch = FetchType.LAZY)
    private Set<Restaurant> restaurants;

    @Override
    public int compareTo(Chef o) {
        if (o != null) {
            if (o.getId() > this.getId()) {
                return -1;
            } else if (o.getId() < this.getId()) {
                return 1;
            } else return 0;
        }
        return 0;
    }
}
