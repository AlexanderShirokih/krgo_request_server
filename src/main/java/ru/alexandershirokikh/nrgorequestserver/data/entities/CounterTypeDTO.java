package ru.alexandershirokikh.nrgorequestserver.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alexandershirokikh.nrgorequestserver.models.CounterAccuracy;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "counter_types")
public class CounterTypeDTO {
    /**
     * Counter ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Counter type name
     */
    @Column(length = 24, nullable = false)
    private String name;


    /**
     * Counter accuracy. Less value means more precise counter
     */
    @Column(precision = 1, nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CounterAccuracy accuracy;


    /**
     * Counter bits count (before decimal place)
     */
    @Column(precision = 1, nullable = false)
    private Integer bits;

    /**
     * {@literal true} if counter is single phased, @{literal false} if it is tree phased
     */
    @Column(nullable = false)
    private Boolean singlePhased;
}
