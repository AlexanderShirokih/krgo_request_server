package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;

/**
 * Describes API request for adding new counter type
 */
@RequiredArgsConstructor
@Data
public class CounterType {

    /**
     * Internal ID
     */
    private final Integer id;

    /**
     * Counter type name
     */
    @NotEmpty
    @Size(max = 24)
    private final String name;

    /**
     * Counter accuracy. Less value means more precise counter
     */
    @NotNull
    private final CounterAccuracy accuracy;

    /**
     * Counter bits count (before decimal place)
     */
    @NotNull
    @Min(3)
    @Max(8)
    private final Integer bits;

    /**
     * {@literal true} if counter is single phased, @{literal false} if it is tree phased
     */
    @NotNull
    private final Boolean singlePhased;

}
