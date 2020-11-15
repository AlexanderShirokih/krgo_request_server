package ru.alexandershirokikh.nrgorequestserver.data.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Describes request type
 */
@NoArgsConstructor
@Data
@Entity(name = "request_type")
public class RequestTypeDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Short name of request type
     */
    @Column(nullable = false, length = 16, unique = true)
    private String shortName;

    /**
     * Full name of request type
     */
    @Column(nullable = false, length = 64)
    private String fullName;
}
