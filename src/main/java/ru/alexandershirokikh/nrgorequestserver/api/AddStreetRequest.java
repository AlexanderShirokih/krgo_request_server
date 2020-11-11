package ru.alexandershirokikh.nrgorequestserver.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * Describes API request to add new street
 */
@NoArgsConstructor
@Data
public class AddStreetRequest {
    /**
     * Street name
     */
    @NotEmpty
    String name;

    /**
     * Optional district id
     */
    @Min(1)
    Integer districtId;
}
