package ru.alexandershirokikh.nrgorequestserver.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity(name = "employee")
@JsonIgnoreProperties("assignments")
public class EmployeeDTO {

    public enum EmployeeStatus {
        WORKS,
        FIRED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 80, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private PositionDTO position;

    @Column(precision = 1, nullable = false)
    private Integer accessGroup;

    @Column
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status = EmployeeStatus.WORKS;

    @JsonIgnoreProperties("assignments")
    @OneToMany(mappedBy = "employee")
    private Set<EmployeeAssignmentDTO> assignments;
}
