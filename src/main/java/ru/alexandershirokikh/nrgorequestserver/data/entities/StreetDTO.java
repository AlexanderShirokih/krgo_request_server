package ru.alexandershirokikh.nrgorequestserver.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Describes a city street
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "street")
public class StreetDTO {

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
    private DistrictDTO district;
}
