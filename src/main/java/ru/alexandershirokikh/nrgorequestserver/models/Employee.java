package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * Describes API data class for employee information interchange
 */
@Data
public class Employee {

    public enum EmployeeStatus {
        WORKS,
        FIRED
    }

    /**
     * Employee id. Used in responses only.
     */
    private final Integer id;

    /**
     * Employee name
     */
    @NotEmpty
    @Size(min = 2, max = 80)
    private final String name;

    /**
     * Employee accessGroup
     */
    @Min(1)
    @Max(5)
    @NotNull
    private final Integer accessGroup;

    /**
     * Employee position
     */
    @NotNull
    private final Position position;

    /**
     * Employee work status
     */
    private final EmployeeStatus status;
}
