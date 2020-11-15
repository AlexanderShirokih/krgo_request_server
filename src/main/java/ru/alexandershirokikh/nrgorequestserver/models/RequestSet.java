package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

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

}