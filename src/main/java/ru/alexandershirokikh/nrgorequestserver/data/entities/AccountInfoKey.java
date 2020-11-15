package ru.alexandershirokikh.nrgorequestserver.data.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Describes a composite primary key for id and revision field
 */
@NoArgsConstructor
@Data
public class AccountInfoKey implements Serializable {

    /**
     * ID key
     */
    private Integer baseId;

    /**
     * Current revision
     */
    private Integer revision;
}
