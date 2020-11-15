package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;

/**
 * Encapsulates employee and its assignment type
 */
@Data
public class EmployeeAssignment {

    /**
     * Assigned employee
     */
    private final Employee employee;

    /**
     * Assignment type
     */
    private final EmployeeAssignmentType type;
}
