package com.practice.cooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Chef")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Chef implements Comparable<Chef> {

    @Transient
    public static final String SEQUENCE_NAME = "chef_seq";

    @Id
    private Long   id;
    private String name;

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
