package ru.alexandershirokikh.nrgorequestserver.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountingPointKey implements Serializable {
    /**
     * Counter number
     */
    private String counterNumber;

    /**
     * Assigned counter type
     */
    private Integer counterTypeId;
}
