package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
 * Describes API request for adding or updating counting point info's
 */
@NoArgsConstructor
@Data
public class CountingPoint {

    /**
     * Counter number
     */
    @NotEmpty
    @Size(min = 1, max = 24)
    private String counterNumber;

    /**
     * Assigned counter type.
     */
    @NotNull
    private Integer counterTypeId;

    /**
     * Transformation substation name
     */
    @Size(min = 1, max = 24)
    private String tpName;

    /**
     * Feeder number
     */
    @Size(max = 2)
    private String feederNumber;

    /**
     * Pillar number
     */
    @Size(max = 3)
    private String pillarNumber;

    /**
     * Nominal power
     */
    @PositiveOrZero
    private Float power;

    /**
     * Counter check year
     */
    @Min(1980)
    @Max(2100)
    private Integer checkYear;

    /**
     * Counter check quarter
     */
    @Min(1)
    @Max(4)
    private Integer checkQuarter;

}
