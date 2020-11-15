package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


/**
 * Describes API request for adding new position
 */
@NoArgsConstructor
@Data
public class Position {
    /**
     * Position name
     */
    @NotEmpty
    @Size(min = 2, max = 32)
    String name;
}