package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Describes API request for adding new request type
 */
@NoArgsConstructor
@Data
public class RequestType {
    /**
     * Short request type name
     */
    @NotEmpty
    @Size(min = 2, max = 16)
    String shortName;

    /**
     * Full request type name
     */
    @NotEmpty
    @Size(min = 2, max = 64)
    String fullName;
}
