package com.practice.cooking.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ChefDto {

    @NotEmpty
    private Long   id;
    // spring validator
    /// validate name provided should not not be optional
    /// also name should be 2 chars min long
    // regex
    @NotEmpty
    @Length(min = 2, message = "Invalid name")
    //@Pattern(regexp = "\\d{5,}", message = "Invalid dealer number")
    private String name;
}
