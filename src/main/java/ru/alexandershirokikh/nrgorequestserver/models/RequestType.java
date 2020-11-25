package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Describes API request for adding new request type
 */
@RequiredArgsConstructor
@Data
public class RequestType {

    /**
     * Internal ID
     */
    final Integer id;

    /**
     * Short request type name
     */
    @NotEmpty
    @Size(min = 2, max = 16)
    final String shortName;

    /**
     * Full request type name
     */
    @NotEmpty
    @Size(min = 2, max = 64)
    final String fullName;
}
