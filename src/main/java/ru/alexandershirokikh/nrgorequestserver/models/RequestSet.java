package ru.alexandershirokikh.nrgorequestserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Describes data class request set
 */
@Data
public class RequestSet {

    /**
     * Request set id
     */
    private final Long id;

    /**
     * Request set name
     */
    @NotEmpty
    @Size(min = 2, max = 24)
    private final String name;

    /**
     * Request set target date
     */
    @NotNull
    private final Date date;

    /**
     * Associated request. Used only for response
     */
    private final List<Request> requests;

    /**
     * Assigned employees. Used only for response
     */
    private final List<EmployeeAssignment> assignedEmployees;

    /**
     * Returns assigned main employee
     *
     * @throws IllegalStateException if has no assigned main employee to the worksheet
     */
    public Employee requireMainEmployee() {
        return getEmployeesOfType(EmployeeAssignmentType.MAIN)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Worksheet has no assigned main employee!"));
    }

    /**
     * Returns assigned chief employee
     *
     * @throws IllegalStateException if has no assigned chief employee to the worksheet
     */

    public Employee requireChiefEmployee() {
        return getEmployeesOfType(EmployeeAssignmentType.CHIEF)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Worksheet has no assigned  chief employee!"));
    }

    /**
     * Returns a stream of member employees
     */
    public Stream<Employee> requireMembersEmployee() {
        return getEmployeesOfType(EmployeeAssignmentType.MEMBER);
    }

    private Stream<Employee> getEmployeesOfType(EmployeeAssignmentType type) {
        if (assignedEmployees == null || assignedEmployees.isEmpty())
            return Stream.empty();

        return assignedEmployees.stream()
                .filter(assignment -> assignment.getType() == type)
                .map(EmployeeAssignment::getEmployee);
    }

    /**
     * Returns a list of work types on this worksheet
     */
    @JsonIgnore
    public Iterable<String> getWorkTypes() {
        return getRequests().stream()
                .map(Request::getRequestType)
                .map(requestType -> requestType.fullName)
                .collect(Collectors.toSet());
    }

    @JsonIgnore
    public String getDay() {
        return new SimpleDateFormat("dd").format(date);
    }

    @JsonIgnore
    public String getMonth() {
        return new SimpleDateFormat("MM").format(date);
    }

    @JsonIgnore
    public String getYear() {
        return new SimpleDateFormat("yyyy г.").format(date);
    }

    @JsonIgnore
    public String getFullDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

}
