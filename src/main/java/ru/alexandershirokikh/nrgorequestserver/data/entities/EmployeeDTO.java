package ru.alexandershirokikh.nrgorequestserver.data.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "employee")
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

}
