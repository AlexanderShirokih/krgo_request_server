package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Size;


/**
 * Describes API request for adding new position
 */
@RequiredArgsConstructor
@Data
public class Position {

    /**
     * Position ID. Used for server responses
     */
    final Integer id;

    /**
     * Position name
     */
    @Size(min = 2, max = 32)
    final String name;
}