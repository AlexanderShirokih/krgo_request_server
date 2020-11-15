package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Describes API request for adding new district
 */
@NoArgsConstructor
@Data
public class District {
    /**
     * District name
     */
    @NotEmpty
    @Size(min = 2, max = 20)
    String name;
}
