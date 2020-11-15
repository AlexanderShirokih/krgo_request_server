package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * Describes API data class for employee information interchange
 */
@Data
public class Employee {

    /**
     * Employee name
     */
    @NotEmpty
    @Size(min = 2, max = 80)
    private String name;

    /**
     * Employee accessGroup
     */
    @Min(1)
    @Max(5)
    @NotNull
    private Integer accessGroup;

    /**
     * Employee position
     */
    @Positive
    @NotNull
    private Integer positionId;

}
