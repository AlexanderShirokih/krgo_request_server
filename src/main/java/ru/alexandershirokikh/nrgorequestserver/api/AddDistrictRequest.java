package ru.alexandershirokikh.nrgorequestserver.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * Describes API request for adding new district
 */
@NoArgsConstructor
@Data
public class AddDistrictRequest {
    /**
     * District name
     */
    @NotEmpty
    String name;
}
