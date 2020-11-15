package ru.alexandershirokikh.nrgorequestserver.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandershirokikh.nrgorequestserver.data.entities.EmployeeDTO;

/**
 * Repository interface for managing employees
 */
public interface EmployeeRepository extends JpaRepository<EmployeeDTO, Integer> {
}
