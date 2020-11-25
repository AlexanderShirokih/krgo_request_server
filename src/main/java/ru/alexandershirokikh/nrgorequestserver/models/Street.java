package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Describes API request to add new street
 */
@RequiredArgsConstructor
@Data
public class Street {

    /**
     * Internal ID
     */
    final Integer id;

    /**
     * Street name
     */
    @NotEmpty
    @Size(max = 32)
    final String name;

    /**
     * Optional district id
     */
    final District district;
}
