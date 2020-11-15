package ru.alexandershirokikh.nrgorequestserver.data.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.alexandershirokikh.nrgorequestserver.models.EmployeeAssignmentType;

import javax.persistence.*;

/**
 * Describes assignments between employee and request set
 */
@Data
@NoArgsConstructor
@Entity(name = "assignment")
public class EmployeeAssignmentDTO {

    @EmbeddedId
    private EmployeeAssignmentKey assignmentKey;

    /**
     * Target request set
     */

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @MapsId("requestSetId")
    @JoinColumn(name = "request_set_id")
    private RequestSetDTO requestSet;

    /**
     * Assigned worker
     */
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "worker_id")
    private EmployeeDTO employee;

    /**
     * Worker assignment kind
     */
    @Column(name = "type")
    @Enumerated(EnumType.STRING)

    private EmployeeAssignmentType assignmentType;

}
