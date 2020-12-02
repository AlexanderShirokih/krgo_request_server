package ru.alexandershirokikh.nrgorequestserver.models;

import lombok.Data;
import ru.alexandershirokikh.nrgorequestserver.utils.StringUtils;

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
    private final CounterType counterType;

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

    /**
     * Combines all counter info fields to string
     */
    public String getCounterInfo() {
        StringBuilder builder = new StringBuilder();
        if (counterNumber != null && !counterNumber.isBlank()) {
            builder.append("№ ");
            builder.append(counterNumber);
            builder.append(' ');
        }

        if (counterType != null) {
            builder.append(counterType.getName());
            builder.append(' ');
        }

        if (checkQuarter != null && checkQuarter > 0) {
            builder.append(StringUtils.toRoman(checkQuarter));
            builder.append('-');
        }
        if (checkYear != null && checkYear > 1000) {
            builder.append(String.valueOf(checkYear).substring(2));
        }

        return builder.toString();
    }


    /**
     * Returns connection point info combined into string
     */
    public String getConnectionPoint() {
        StringBuilder builder = new StringBuilder();

        if (tpName != null && !tpName.isEmpty()) {
            builder.append("ТП: ");
            builder.append(tpName);
            builder.append(" ");
        }

        if (feederNumber != null) {
            builder.append("Ф. ");
            builder.append(feederNumber);
            builder.append(" ");
        }

        return builder.toString();
    }

}
