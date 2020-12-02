package ru.alexandershirokikh.nrgorequestserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.alexandershirokikh.nrgorequestserver.utils.StringUtils;

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


    @JsonIgnore
    public String getNameWithGroup() {
        return name + " " + StringUtils.toRoman(accessGroup) + " гр.";
    }

    @JsonIgnore
    public String getNameWithPosition() {
        return position.getName() + " " + name;
    }

}
