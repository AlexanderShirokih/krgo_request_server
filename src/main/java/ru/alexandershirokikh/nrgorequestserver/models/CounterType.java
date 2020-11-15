package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
 * Describes API request for adding new counter type
 */
@NoArgsConstructor
@Data
public class CounterType {

    /**
     * Counter type name
     */
    @NotEmpty
    @Size(max = 24)
    private String name;

    /**
     * Counter accuracy. Less value means more precise counter
     */
    @NotNull
    private CounterAccuracy accuracy;

    /**
     * Counter bits count (before decimal place)
     */
    @NotNull
    @Min(3)
    @Max(8)
    private Integer bits;

    /**
     * {@literal true} if counter is single phased, @{literal false} if it is tree phased
     */
    @NotNull
    private Boolean singlePhased;

}
