package ru.alexandershirokikh.nrgorequestserver.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Composite primary key for {@code EmployeeAssignmentDTO}
 */
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAssignmentKey implements Serializable {

    @Column(name = "request_set_id")
    private Long requestSetId;

    @Column(name = "worker_id")
    private Integer employeeId;
}
