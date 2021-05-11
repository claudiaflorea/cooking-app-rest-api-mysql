package com.practice.cooking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private Long id;

    @Column(name = "c_name")
    private String name;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chefs")
//    @JsonIgnoreProperties("chefs")
//    private Set<Restaurant> restaurants;

    @Override
    public String toString() {
        return "Chef[" +
            "id=" + id +
            ", name='" + name + '\'' +
            ']';
    }

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
