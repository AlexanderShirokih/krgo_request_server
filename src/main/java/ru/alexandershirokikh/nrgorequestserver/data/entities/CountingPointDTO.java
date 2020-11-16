package ru.alexandershirokikh.nrgorequestserver.data.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "counter_point")
@IdClass(CountingPointKey.class)
public class CountingPointDTO {

    /**
     * Counter number
     */
    @Id
    @Column(length = 24, nullable = false)
    private String counterNumber;

    /**
     * Assigned counter type
     */
    @Id
    @Column(name = "counter_type_id", nullable = false)
    private Integer counterTypeId;

    /**
     * Transformation substation name
     */
    @Column(length = 8)
    private String tpName;

    /**
     * Feeder number
     */
    @Column(length = 2)
    private Integer feederNumber;

    /**
     * Pillar number
     */
    @Column(length = 5)
    private String pillarNumber;

    /**
     * Nominal power
     */
    @Column
    private Float power;

    /**
     * Counter check year
     */
    @Column(precision = 4, nullable = false)
    private Integer checkYear;

    /**
     * Counter check quarter
     */
    @Column(precision = 1, nullable = false)
    private Integer checkQuarter;

}
