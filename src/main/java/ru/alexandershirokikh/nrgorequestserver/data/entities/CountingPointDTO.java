package ru.alexandershirokikh.nrgorequestserver.data.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "counter_point")
public class CountingPointDTO {

    @EmbeddedId
    private CountingPointPK key;

    /**
     * Assigned counter type
     */
    @ManyToOne
    @JoinColumn(name = "counter_type_id", nullable = false)
    @MapsId("counterTypeId")
    private CounterTypeDTO counterType;

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
