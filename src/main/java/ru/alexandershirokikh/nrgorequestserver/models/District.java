package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Describes API request for adding new district
 */
@RequiredArgsConstructor
@Data
public class District {
    /**
     * Internal ID
     */
    final Integer id;
    /**
     * District name
     */
    @NotEmpty
    @Size(min = 2, max = 20)
    final String name;
}
