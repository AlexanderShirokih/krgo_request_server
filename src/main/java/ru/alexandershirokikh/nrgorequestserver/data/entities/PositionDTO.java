package ru.alexandershirokikh.nrgorequestserver.data.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Describes worker position
 */
@Data
@NoArgsConstructor
@Entity(name = "positions")
public class PositionDTO {

    /**
     * Position ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Position name
     */
    @Column(length = 32, nullable = false)
    private String name;

}
