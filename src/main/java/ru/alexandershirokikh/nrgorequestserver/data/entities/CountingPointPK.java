package ru.alexandershirokikh.nrgorequestserver.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CountingPointPK implements Serializable {
    /**
     * Counter number
     */
    private String counterNumber;

    /**
     * Assigned counter type
     */
    private Integer counterTypeId;
}
