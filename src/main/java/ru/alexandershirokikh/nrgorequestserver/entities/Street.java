package ru.alexandershirokikh.nrgorequestserver.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Describes a city street
 */
@Data
@NoArgsConstructor
@Entity(name = "street")
public class Street {

    /**
     * Street ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Street name
     */
    @Column(length = 32, nullable = false, unique = true)
    private String name;


    /**
     * Assigned district. May be {@literal null}
     */
    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;
}
