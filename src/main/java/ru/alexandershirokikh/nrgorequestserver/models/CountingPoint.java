package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * Describes API request for adding or updating counting point info's
 */
@Data
public class CountingPoint {

    /**
     * Counter number
     */
    @NotEmpty
    @Size(min = 1, max = 24)
    private final String counterNumber;

    /**
     * Assigned counter type.
     */
    @NotNull
    private final Integer counterTypeId;

    /**
     * Transformation substation name
     */
    @Size(min = 1, max = 24)
    private final String tpName;

    /**
     * Feeder number
     */
    @Max(99)
    private final Integer feederNumber;

    /**
     * Pillar number
     */
    @Size(max = 5)
    private final String pillarNumber;

    /**
     * Nominal power
     */
    @PositiveOrZero
    private final Float power;

    /**
     * Counter check year
     */
    @Min(1980)
    @Max(2100)
    private final Integer checkYear;

    /**
     * Counter check quarter
     */
    @Min(1)
    @Max(4)
    private final Integer checkQuarter;

}
