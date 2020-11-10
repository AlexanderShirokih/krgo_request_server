package ru.alexandershirokikh.nrgorequestserver.entities;

import lombok.*;

import javax.persistence.*;

/**
 * Describes city district
 */
@Data
@NoArgsConstructor
@Entity(name = "district")
public class District {

    /**
     * District ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * District name
     */
    @Column(length = 20, nullable = false, unique = true)
    private String name;
}
