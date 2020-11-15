package ru.alexandershirokikh.nrgorequestserver.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.EmployeeAssignmentDTO;
import ru.alexandershirokikh.nrgorequestserver.data.entities.EmployeeAssignmentKey;

public interface EmployeeAssignmentRepository extends JpaRepository<EmployeeAssignmentDTO, EmployeeAssignmentKey> {
}
