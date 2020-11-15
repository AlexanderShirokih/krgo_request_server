package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Describes API request to add new street
 */
@NoArgsConstructor
@Data
public class Street {
    /**
     * Street name
     */
    @NotEmpty
    @Size(max = 32)
    String name;

    /**
     * Optional district id
     */
    @Min(1)
    Integer districtId;
}
